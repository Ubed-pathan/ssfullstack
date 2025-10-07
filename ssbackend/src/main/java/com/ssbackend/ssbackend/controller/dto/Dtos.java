package com.ssbackend.ssbackend.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;

public class Dtos {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CountryCreateReq(@NotBlank String name) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CountryUpdateReq(@NotBlank String name) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record StateCreateReq(@NotBlank String name, @NotBlank String country) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record StateUpdateReq(@NotBlank String name, @NotBlank String country) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DistrictCreateReq(@NotBlank String name, @NotBlank String state) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DistrictUpdateReq(@NotBlank String name, @NotBlank String state) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record LanguageCreateReq(@NotBlank String name) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record LanguageUpdateReq(@NotBlank String name) {}
}
