package com.ssbackend.ssbackend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Dtos {
    public record CountryCreateReq(@NotBlank String name, String code) {}
    public record CountryUpdateReq(@NotBlank String name, String code) {}

    public record StateCreateReq(@NotBlank String name, String code, @NotNull Long countryId) {}
    public record StateUpdateReq(@NotBlank String name, String code, @NotNull Long countryId) {}

    public record DistrictCreateReq(@NotBlank String name, @NotNull Long stateId) {}
    public record DistrictUpdateReq(@NotBlank String name, @NotNull Long stateId) {}

    public record LanguageCreateReq(@NotBlank String name, String code) {}
    public record LanguageUpdateReq(@NotBlank String name, String code) {}
}
