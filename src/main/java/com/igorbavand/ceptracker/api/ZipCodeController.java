package com.igorbavand.ceptracker.api;

import com.igorbavand.ceptracker.application.dto.response.ZipCodeAuditResponse;
import com.igorbavand.ceptracker.application.dto.response.ZipCodeResponse;
import com.igorbavand.ceptracker.application.service.ZipCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/zip-code")
public class ZipCodeController {

    private final ZipCodeService service;

    public ZipCodeController(ZipCodeService service) {
        this.service = service;
    }

    @GetMapping("{zipCode}")
    public ResponseEntity<ZipCodeResponse> getZipCode(@PathVariable String zipCode) {
        return ResponseEntity.ok().body(service.findInfoZipCode(zipCode));
    }

    @GetMapping("audit")
    public ResponseEntity<List<ZipCodeAuditResponse>> getZipCodesAudit() {
        return ResponseEntity.ok().body(service.findAllZipCodesAudit());
    }

}
