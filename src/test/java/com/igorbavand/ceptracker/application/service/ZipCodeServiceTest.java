package com.igorbavand.ceptracker.application.service;

import com.igorbavand.ceptracker.application.dto.response.ZipCodeAuditResponse;
import com.igorbavand.ceptracker.application.dto.response.ZipCodeResponse;
import com.igorbavand.ceptracker.application.mapper.ZipCodeMapper;
import com.igorbavand.ceptracker.domain.model.User;
import com.igorbavand.ceptracker.domain.model.ZipCode;
import com.igorbavand.ceptracker.infrastructure.client.ZipCodeClient;
import com.igorbavand.ceptracker.infrastructure.repository.ZipCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ZipCodeServiceTest {

    @InjectMocks
    private ZipCodeService zipCodeService;

    @Mock
    private ZipCodeClient zipCodeClient;

    @Mock
    private ZipCodeRepository repository;

    @Mock
    private ZipCodeMapper mapper;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findInfoZipCode_zipCodeNotFoundInDatabase_fetchesFromClientAndSaves() {
        String zipCode = "12345678";
        ZipCodeResponse clientResponse = new ZipCodeResponse();
        User mockedUser = new User();
        mockedUser.setUsername("testUser");
        ZipCode zipCodeEntity = new ZipCode();

        when(repository.findAllByZipCodeIgnoringDash(zipCode)).thenReturn(List.of());
        when(zipCodeClient.getZipCode(zipCode)).thenReturn(clientResponse);
        when(mapper.responseToZipCode(clientResponse)).thenReturn(zipCodeEntity);
        when(userService.getLoggedUser()).thenReturn(mockedUser);

        ZipCodeResponse actualResponse = zipCodeService.findInfoZipCode(zipCode);

        assertNotNull(actualResponse);
        assertEquals(clientResponse, actualResponse);
        verify(zipCodeClient, times(1)).getZipCode(zipCode);
        verify(mapper, times(1)).responseToZipCode(clientResponse);
        verify(repository, times(1)).save(zipCodeEntity);
    }

    @Test
    void findAllZipCodesAudit_returnsMappedAuditResponseList() {
        List<ZipCode> zipCodeList = List.of(new ZipCode(), new ZipCode());
        List<ZipCodeAuditResponse> expectedAuditResponseList = List.of(new ZipCodeAuditResponse(), new ZipCodeAuditResponse());

        when(repository.findAll()).thenReturn(zipCodeList);
        when(mapper.zipCodeListToAuditResponseList(zipCodeList)).thenReturn(expectedAuditResponseList);

        List<ZipCodeAuditResponse> actualAuditResponseList = zipCodeService.findAllZipCodesAudit();

        assertNotNull(actualAuditResponseList);
        assertEquals(expectedAuditResponseList, actualAuditResponseList);
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).zipCodeListToAuditResponseList(zipCodeList);
    }
}
