package com.spring.boot.Repo.Security;

import com.spring.boot.Model.Security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByCode(String code);
}
