package com.igorbavand.ceptracker.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZipCodeAuditResponse extends ZipCodeResponse{
    private String user;
}
