package com.igorbavand.ceptracker.infrastructure;

import com.igorbavand.ceptracker.domain.model.ZipCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZipCodeRepository extends JpaRepository<ZipCode, Integer> {
}
