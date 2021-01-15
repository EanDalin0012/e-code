package com.ecode.core.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String storeFile(MultipartFile file);

    byte[] storeFile(String filePath) throws IOException;
}
