package com.igorbavand.ceptracker.infrastructure;

import com.igorbavand.ceptracker.domain.model.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZipCodeRepository extends JpaRepository<ZipCode, Long> {

    @Query("SELECT z FROM ZipCode z WHERE REPLACE(z.zipCode, '-', '') = REPLACE(:zipCode, '-', '')")
    List<ZipCode> findAllByZipCodeIgnoringDash(String zipCode);
}
