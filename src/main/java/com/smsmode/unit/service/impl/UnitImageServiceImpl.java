/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.dao.service.ImageDaoService;
import com.smsmode.unit.dao.service.UnitDaoService;
import com.smsmode.unit.dao.specification.ImageSpecification;
import com.smsmode.unit.dao.specification.UnitSpecification;
import com.smsmode.unit.exception.InternalServerException;
import com.smsmode.unit.exception.ResourceNotFoundException;
import com.smsmode.unit.exception.enumeration.InternalServerExceptionTitleEnum;
import com.smsmode.unit.exception.enumeration.ResourceNotFoundExceptionTitleEnum;
import com.smsmode.unit.mapper.ImageMapper;
import com.smsmode.unit.model.ImageModel;
import com.smsmode.unit.model.UnitModel;
import com.smsmode.unit.resource.image.ImageGetResource;
import com.smsmode.unit.resource.image.ImagePatchResource;
import com.smsmode.unit.service.StorageService;
import com.smsmode.unit.service.UnitImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 19 May 2025</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UnitImageServiceImpl implements UnitImageService {

    private final UnitDaoService unitDaoService;
    private final ImageDaoService imageDaoService;
    private final StorageService storageService;
    private final ImageMapper imageMapper;

    @Override
    public ResponseEntity<Page<ImageGetResource>> retrieveImages(String unitId, Pageable pageable) {
        Page<ImageModel> imageModels = imageDaoService.findAllBy(ImageSpecification.withUnitIdEqual(unitId), pageable);
        return ResponseEntity.ok(imageModels.map(imageMapper::modelToImageGetResource));
    }

    @Override
    public ResponseEntity<Resource> retrieveImage(String imageId) {

        ImageModel image = imageDaoService.findOneBy(ImageSpecification.withId(imageId));

        String imagePath = storageService.generateUnitImagePath(image);
        File file = new File(imagePath);
        if (file.exists()) {
            log.debug("file exists");
            Resource resource = null;
            try {
                byte[] bytes = FileUtils.readFileToByteArray(file);
                resource = new InputStreamResource(new ByteArrayInputStream(bytes));
            } catch (IOException e) {
                log.debug("An error has been thrown while to convert {} to byte stream", file);
                throw new InternalServerException(InternalServerExceptionTitleEnum.FILE_UPLOAD, "An error occurred while trying convert file to byte stream");
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + image.getFileName())
                    .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                    .body(resource);
        } else {
            throw new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.IMAGE_NOT_FOUND,
                    "No image found with the specified criteria");
        }
    }

    @Override
    public ResponseEntity<ImageGetResource> createImage(String unitId, MultipartFile file) {

        UnitModel unit = unitDaoService.findOneBy(UnitSpecification.withIdEqual(unitId));

        ImageModel image = new ImageModel();
        image.setFileName(file.getOriginalFilename());
        image.setUnit(unit);
        if (!imageDaoService.existsBy(ImageSpecification.withUnitIdEqual(unitId))) {
            image.setCover(true);
        }
        image = imageDaoService.save(image);

        String imagePath = storageService.generateUnitImagePath(image);

        try {
            String imageFileName = storageService.storeFile(imagePath, file.getInputStream());
            if (ObjectUtils.isEmpty(imageFileName)) {
                imageDaoService.deleteBy(ImageSpecification.withId(image.getId()));
                throw new InternalServerException(InternalServerExceptionTitleEnum.FILE_UPLOAD, "An unexpected error occurred while saving the image. Please try again later.");
            }
        } catch (IOException e) {
            log.warn("An error occurred when storing image file", e);
            imageDaoService.deleteBy(ImageSpecification.withId(image.getId()));
            throw new InternalServerException(InternalServerExceptionTitleEnum.FILE_UPLOAD, "An unexpected error occurred while saving the image. Please try again later.");
        }
        return ResponseEntity.created(URI.create("")).body(imageMapper.modelToImageGetResource(image));
    }

    @Override
    public ResponseEntity<ImageGetResource> updateById(String imageId, ImagePatchResource imagePatchResource) {
        ImageModel image = imageDaoService.findOneBy(ImageSpecification.withId(imageId));
        image.setCover(imagePatchResource.isCover());
        if (imageDaoService.existsBy(ImageSpecification.withCover(true))) {
            ImageModel coverImage = imageDaoService.findOneBy(ImageSpecification.withCover(true));
            coverImage.setCover(false);
            imageDaoService.save(coverImage);
        }
        image = imageDaoService.save(image);
        return ResponseEntity.ok(imageMapper.modelToImageGetResource(image));
    }

    @Override
    public ResponseEntity<Void> removeById(String imageId) {
        if (imageDaoService.existsBy(ImageSpecification.withId(imageId))) {
            ImageModel image = imageDaoService.findOneBy(ImageSpecification.withId(imageId));
            String imagePath = storageService.generateUnitImagePath(image);
            storageService.deleteFile(imagePath);
            imageDaoService.deleteBy(ImageSpecification.withId(imageId));
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException(
                    ResourceNotFoundExceptionTitleEnum.IMAGE_NOT_FOUND,
                    "No image found with the specified criteria");
        }
    }

}
