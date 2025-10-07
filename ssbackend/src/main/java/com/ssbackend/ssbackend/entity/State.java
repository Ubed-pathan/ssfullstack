package com.ssbackend.ssbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "states", indexes = {
        @Index(name = "idx_state_country", columnList = "country")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class State extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    // Store country as plain text (no foreign key)
    @NotBlank
    @Column(nullable = false, length = 100)
    private String country;
}
