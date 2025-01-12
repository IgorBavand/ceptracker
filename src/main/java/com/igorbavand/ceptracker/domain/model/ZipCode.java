package com.igorbavand.ceptracker.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "zip_code")
@Builder
public class ZipCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;
    private String street;
    private String complement;
    private String unit;
    private String neighborhood;
    private String city;
    private String state;
    private String region;
    private String ibge;
    private String gia;
    private String ddd;
    private String siafi;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created;

    @PrePersist
    protected void onCreate() {
        this.created = LocalDateTime.now();
    }
}
