package com.igorbavand.ceptracker.application.service;

import com.igorbavand.ceptracker.application.dto.response.ZipCodeAuditResponse;
import com.igorbavand.ceptracker.application.dto.response.ZipCodeResponse;
import com.igorbavand.ceptracker.application.mapper.ZipCodeMapper;
import com.igorbavand.ceptracker.domain.model.ZipCode;
import com.igorbavand.ceptracker.infrastructure.ZipCodeRepository;
import com.igorbavand.ceptracker.infrastructure.client.ZipCodeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ZipCodeService {

    private final ZipCodeClient zipCodeClient;
    private final ZipCodeRepository repository;
    private final ZipCodeMapper mapper;
    private final UserService userService;

    public ZipCodeService(ZipCodeClient zipCodeClient,
                          ZipCodeRepository repository,
                          ZipCodeMapper mapper,
                          UserService userService) {
        this.zipCodeClient = zipCodeClient;
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
    }

    public ZipCodeResponse findInfoZipCode(String zipCode) {
        List<ZipCode> zipCodeList = repository.findAllByZipCodeIgnoringDash(zipCode);
        ZipCodeResponse zipCodeResponse = new ZipCodeResponse();

        if (!zipCodeList.isEmpty()) {
            log.info("Found zip code {}", zipCode);
            zipCodeResponse = mapper.zipCodeToResponse(zipCodeList.get(0));
        } else {
            log.info("Waiting for information, zip code: {}", zipCode);
            zipCodeResponse = zipCodeClient.getZipCode(zipCode);
        }

        ZipCode zipCodeToSave = mapper.responseToZipCode(zipCodeResponse);
        zipCodeToSave.setUser(userService.getLoggedUser());
        repository.save(zipCodeToSave);

        return zipCodeResponse;
    }

    public List<ZipCodeAuditResponse> findAllZipCodesAudit() {
        List<ZipCode> zipCodeList = repository.findAll();
        return mapper.zipCodeListToAuditResponseList(zipCodeList);
    }

}
