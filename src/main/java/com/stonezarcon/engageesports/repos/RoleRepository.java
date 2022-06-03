package com.stonezarcon.engageesports.repos;

import com.stonezarcon.engageesports.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
