package com.company.miniecom.repository;

import com.company.miniecom.domains.auth.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Long> {
}
