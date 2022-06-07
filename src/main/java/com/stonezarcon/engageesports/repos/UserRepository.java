package com.stonezarcon.engageesports.repos;


import com.stonezarcon.engageesports.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long Long);

    @Query(value = "SELECT * FROM USERS WHERE CLASS_ID = :id",
    nativeQuery = true)
    List<User> findUsersByClassId(@Param("id") Long id);
}
