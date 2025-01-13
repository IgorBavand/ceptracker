package com.igorbavand.ceptracker.application.mapper;

import com.igorbavand.ceptracker.application.dto.response.ZipCodeAuditResponse;
import com.igorbavand.ceptracker.application.dto.response.ZipCodeResponse;
import com.igorbavand.ceptracker.domain.model.User;
import com.igorbavand.ceptracker.domain.model.ZipCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ZipCodeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "created", ignore = true)
    ZipCode responseToZipCode(ZipCodeResponse zipCodeResponse);

    ZipCodeResponse zipCodeToResponse(ZipCode zipCode);

    @Mapping(source = "user.username", target = "user")
    List<ZipCodeAuditResponse> zipCodeListToAuditResponseList(List<ZipCode> zipCodeList);

    default String map(User user) {
        return user != null ? user.getUsername() : null;
    }
}
