package com.igorbavand.ceptracker.application.mapper;

import com.igorbavand.ceptracker.application.dto.response.ZipCodeResponse;
import com.igorbavand.ceptracker.domain.model.ZipCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZipCodeMapper {

    ZipCode responseToZipCode(ZipCodeResponse zipCodeResponse);
}
