package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.PropertyDaoService;
import com.smsmode.unit.dao.specification.PropertySpecification;
import com.smsmode.unit.embeddable.MediaRefEmbeddable;
import com.smsmode.unit.exception.InternalServerException;
import com.smsmode.unit.exception.enumeration.InternalServerExceptionTitleEnum;
import com.smsmode.unit.mapper.PropertyMapper;
import com.smsmode.unit.model.PropertyModel;
import com.smsmode.unit.resource.image.MediaGetResource;
import com.smsmode.unit.resource.property.PropertyGetResource;
import com.smsmode.unit.resource.property.PropertyPatchResource;
import com.smsmode.unit.resource.property.PropertyPostResource;
import com.smsmode.unit.service.PropertyService;
import com.smsmode.unit.service.feign.MediaFeignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final MediaFeignService mediaFeignService;
    private final PropertyDaoService propertyDaoService;
    private final PropertyMapper propertyMapper;

    @Value("${file.upload.logo-image}")
    public String propertyLogoPath;

    @Override
    @Transactional
    public ResponseEntity<PropertyGetResource> create(PropertyPostResource propertyPostResource, MultipartFile logoFile) {
        PropertyModel property = propertyMapper.postToModel(propertyPostResource);
        property = propertyDaoService.save(property);

        if (logoFile != null && !logoFile.isEmpty()) {
            String filePath = propertyLogoPath.replace(":propertyId", property.getId());
            ResponseEntity<List<MediaGetResource>> response = mediaFeignService.uploadMedia(filePath, new MultipartFile[]{logoFile});
            List<MediaGetResource> mediaList = response.getBody();

            if (mediaList == null || mediaList.isEmpty()) {
                throw new InternalServerException(
                        InternalServerExceptionTitleEnum.FILE_UPLOAD,
                        "Logo upload failed or returned no file."
                );
            }

            MediaRefEmbeddable logo = new MediaRefEmbeddable();
            logo.setUuid(mediaList.getFirst().getId());
            property.setLogoId(logo);

            property = propertyDaoService.save(property);
        }

        return ResponseEntity.created(URI.create("")).body(propertyMapper.modelToGetResource(property));
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PropertyGetResource> getCurrent() {
        log.debug("Retrieving current property");

        Page<PropertyModel> propertyPage = propertyDaoService.findAll(Pageable.unpaged());
        log.info("Retrieved {} properties from database", propertyPage.getTotalElements());

        if (propertyPage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PropertyModel property = propertyPage.getContent().getFirst();
        PropertyGetResource resource = propertyMapper.modelToGetResource(property);
        return ResponseEntity.ok(resource);
    }

    @Override
    @Transactional
    public ResponseEntity<PropertyGetResource> updateById(String id, PropertyPatchResource patchResource, MultipartFile logoFile) {
        log.debug("Updating property with ID: {}", id);

        Specification<PropertyModel> spec = PropertySpecification.withIdEqual(id);
        PropertyModel existingProperty = propertyDaoService.findOneBy(spec);

        if (logoFile != null && !logoFile.isEmpty()) {
            if (existingProperty.getLogoId() != null && existingProperty.getLogoId().getUuid() != null) {
                try {
                    mediaFeignService.deleteMediaById(existingProperty.getLogoId().getUuid());
                    log.info("Deleted old logo for property {}", existingProperty.getId());
                } catch (Exception e) {
                    log.warn("Failed to delete old logo for property {}. Proceeding with update.", existingProperty.getId(), e);
                }
            }
        }

        PropertyModel updatedProperty = propertyMapper.patchResourceToModel(patchResource, existingProperty);

        if (logoFile != null && !logoFile.isEmpty()) {
            String filePath = propertyLogoPath.replace(":propertyId", existingProperty.getId());
            ResponseEntity<List<MediaGetResource>> mediaResponse = mediaFeignService.uploadMedia(filePath, new MultipartFile[]{logoFile});
            List<MediaGetResource> mediaList = mediaResponse.getBody();

            if (mediaList == null || mediaList.isEmpty()) {
                throw new InternalServerException(InternalServerExceptionTitleEnum.FILE_UPLOAD, "Media upload failed.");
            }

            MediaRefEmbeddable logoRef = new MediaRefEmbeddable();
            logoRef.setUuid(mediaList.getFirst().getId());
            updatedProperty.setLogoId(logoRef);
        }

        updatedProperty = propertyDaoService.save(updatedProperty);
        PropertyGetResource resource = propertyMapper.modelToGetResource(updatedProperty);
        return ResponseEntity.ok(resource);
    }

}
