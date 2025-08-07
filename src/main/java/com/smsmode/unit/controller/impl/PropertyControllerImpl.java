package com.smsmode.unit.controller.impl;

import com.smsmode.unit.controller.PropertyController;
import com.smsmode.unit.resource.property.PropertyGetResource;
import com.smsmode.unit.resource.property.PropertyPatchResource;
import com.smsmode.unit.resource.property.PropertyPostResource;
import com.smsmode.unit.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PropertyControllerImpl implements PropertyController {

    private final PropertyService propertyService;

    @Override
    public ResponseEntity<PropertyGetResource> post(PropertyPostResource propertyPostResource, MultipartFile logo) {
        return propertyService.create(propertyPostResource, logo);
    }

    @Override
    public ResponseEntity<PropertyGetResource> getCurrent() {
        return propertyService.getCurrent();
    }

    @Override
    public ResponseEntity<PropertyGetResource> patchById(String id, PropertyPatchResource propertyPatchResource, MultipartFile logoFile) {
        return propertyService.updateById(id, propertyPatchResource, logoFile);
    }
}
