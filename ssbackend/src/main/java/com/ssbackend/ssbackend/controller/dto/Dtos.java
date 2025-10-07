package com.ssbackend.ssbackend.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Dtos {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CountryCreateReq(@NotBlank String name) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CountryUpdateReq(@NotBlank String name) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record StateCreateReq(@NotBlank String name, @NotNull Long countryId) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record StateUpdateReq(@NotBlank String name, @NotNull Long countryId) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DistrictCreateReq(@NotBlank String name, @NotNull Long stateId) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DistrictUpdateReq(@NotBlank String name, @NotNull Long stateId) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record LanguageCreateReq(@NotBlank String name) {}
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record LanguageUpdateReq(@NotBlank String name) {}
}
