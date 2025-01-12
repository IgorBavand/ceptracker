package com.igorbavand.ceptracker.infrastructure;

import com.igorbavand.ceptracker.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}