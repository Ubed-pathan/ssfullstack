package com.ssbackend.ssbackend.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryUploadService {
    String uploadImage(MultipartFile file, String folder) throws Exception;
}
