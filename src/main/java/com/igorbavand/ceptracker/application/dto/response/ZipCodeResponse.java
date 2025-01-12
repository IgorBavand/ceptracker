package com.igorbavand.ceptracker.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZipCodeResponse {
    @JsonProperty("zipCode")
    private String zipCode;
    @JsonProperty("street")
    private String street;
    @JsonProperty("complement")
    private String complement;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("neighborhood")
    private String neighborhood;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("region")
    private String region;
    @JsonProperty("ibge")
    private String ibge;
    @JsonProperty("gia")
    private String gia;
    @JsonProperty("ddd")
    private String ddd;
    @JsonProperty("siafi")
    private String siafi;
}
