/**
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 */
package com.smsmode.unit.service.impl;

import com.smsmode.unit.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Created 28 Apr 2025</p>
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {


    public String storeFile(String path, InputStream inputStream) {
        Path originalFilePath = Paths.get(path);
        try {
            if (!Files.exists(originalFilePath)) {
                Files.createDirectories(originalFilePath.getParent());
                log.info("Directory structure is created ...");
            }
            Files.copy(inputStream, originalFilePath, StandardCopyOption.REPLACE_EXISTING);
            return originalFilePath.getFileName().toString();
        } catch (IOException e) {
            log.warn("An error occurred while saving file. Error details: {}", e.getMessage());
            return null;
        }
    }


    @Override
    public void deleteFile(String path) {
        File file = new File(path);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                log.info("File: '{}' delete successfully.", path);
            } else {
                log.warn("File: '{}' deletion failed.", path);
            }
        } else {
            log.warn("File: '{}' does not exists.", path);
        }
    }
}
