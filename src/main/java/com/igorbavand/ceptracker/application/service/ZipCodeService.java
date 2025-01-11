package com.igorbavand.ceptracker.application.service;

import com.igorbavand.ceptracker.application.dto.response.ZipCodeResponse;
import com.igorbavand.ceptracker.domain.model.ZipCode;
import com.igorbavand.ceptracker.infrastructure.ZipCodeRepository;
import com.igorbavand.ceptracker.infrastructure.client.ZipCodeClient;
import org.springframework.stereotype.Service;

@Service
public class ZipCodeService {

    private final ZipCodeClient zipCodeClient;
    private final ZipCodeRepository repository;

    public ZipCodeService(ZipCodeClient zipCodeClient,
                          ZipCodeRepository repository) {
        this.zipCodeClient = zipCodeClient;
        this.repository = repository;
    }

    public ZipCodeResponse findInfoZipCode(String zipCode) {
        ZipCodeResponse zipCodeResponse = zipCodeClient.getZipCode(zipCode);

        return zipCodeResponse;
    }

}
