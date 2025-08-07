package com.smsmode.unit.service;

import com.smsmode.unit.resource.property.PropertyGetResource;
import com.smsmode.unit.resource.property.PropertyPatchResource;
import com.smsmode.unit.resource.property.PropertyPostResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PropertyService {
    ResponseEntity<PropertyGetResource> create(PropertyPostResource propertyPostResource, MultipartFile logoFile);

    ResponseEntity<PropertyGetResource> getCurrent();

    ResponseEntity<PropertyGetResource> updateById(String id, PropertyPatchResource patchResource, MultipartFile logoFile);

}
