package com.ssbackend.ssbackend.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssbackend.ssbackend.service.CloudinaryUploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
public class UploadController {
    private final CloudinaryUploadService uploadService;

    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("status", "ok");
    }

    // Upload a customer image; returns { url: "..." }
    @PostMapping(value = "/customer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> uploadCustomerImage(@RequestParam("file") MultipartFile file) throws Exception {
        String url = uploadService.uploadImage(file, "ssfullstack/customer images");
        return Map.of("url", url);
    }
}
