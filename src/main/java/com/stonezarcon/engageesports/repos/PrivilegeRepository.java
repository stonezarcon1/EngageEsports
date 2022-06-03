package com.stonezarcon.engageesports.repos;

import com.stonezarcon.engageesports.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
