package com.ssbackend.ssbackend.service.impl;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssbackend.ssbackend.service.CloudinaryUploadService;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryUploadServiceImpl implements CloudinaryUploadService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file, String folder) throws IOException {
        Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), Map.of(
                "folder", folder,
                "resource_type", "image"
        ));
        Object secureUrl = result.get("secure_url");
        return secureUrl != null ? secureUrl.toString() : (String) result.get("url");
    }
}
