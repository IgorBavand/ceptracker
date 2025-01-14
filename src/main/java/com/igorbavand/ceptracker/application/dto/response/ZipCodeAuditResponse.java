package com.igorbavand.ceptracker.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZipCodeAuditResponse extends ZipCodeResponse {

    private String user;

    @JsonFormat(pattern = "dd/MM/yyyy 'às' HH:mm")
    private LocalDateTime created;
}
