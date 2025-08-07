package com.smsmode.unit.controller;

import com.smsmode.unit.resource.property.PropertyGetResource;
import com.smsmode.unit.resource.property.PropertyPostResource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/properties")
public interface PropertyController {

    @PostMapping(consumes = "multipart/form-data")
    ResponseEntity<PropertyGetResource> post(
            @RequestPart("payload") @Valid PropertyPostResource propertyPostResource,
            @RequestPart(value = "logo", required = false) MultipartFile logo
    );

    @GetMapping
    ResponseEntity<PropertyGetResource> getCurrent();

}
