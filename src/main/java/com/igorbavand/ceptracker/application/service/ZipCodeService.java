package com.igorbavand.ceptracker.application.service;

import com.igorbavand.ceptracker.application.dto.response.ZipCodeResponse;
import com.igorbavand.ceptracker.application.mapper.ZipCodeMapper;
import com.igorbavand.ceptracker.domain.model.ZipCode;
import com.igorbavand.ceptracker.infrastructure.ZipCodeRepository;
import com.igorbavand.ceptracker.infrastructure.client.ZipCodeClient;
import org.springframework.stereotype.Service;

@Service
public class ZipCodeService {

    private final ZipCodeClient zipCodeClient;
    private final ZipCodeRepository repository;
    private final ZipCodeMapper mapper;

    public ZipCodeService(ZipCodeClient zipCodeClient,
                          ZipCodeRepository repository,
                          ZipCodeMapper mapper) {
        this.zipCodeClient = zipCodeClient;
        this.repository = repository;
        this.mapper = mapper;
    }

    public ZipCodeResponse findInfoZipCode(String zipCode) {
        ZipCodeResponse zipCodeResponse = zipCodeClient.getZipCode(zipCode);
        ZipCode saveLog = mapper.responseToZipCode(zipCodeResponse);
        repository.save(saveLog);

        return zipCodeResponse;
    }

}
