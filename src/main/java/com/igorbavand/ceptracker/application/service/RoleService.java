package com.igorbavand.ceptracker.application.service;

import com.igorbavand.ceptracker.domain.model.Role;
import com.igorbavand.ceptracker.enums.RoleName;
import com.igorbavand.ceptracker.exception.NotFoundException;
import com.igorbavand.ceptracker.infrastructure.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository roleRepository) {
        this.repository = roleRepository;
    }

    public Role findByName(RoleName name) {
        return repository.findByName(name).orElseThrow(
                () -> new NotFoundException("Permission not found.")
        );
    }

    public Role saveRole(Role role) {
        return repository.save(role);
    }

}
