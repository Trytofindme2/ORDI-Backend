package com.example.ordi2.repo;

import com.example.ordi2.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface adminRepo extends JpaRepository<Admin, UUID>
{
    Optional<Admin> findAdminById(UUID id);

    Optional<Admin> findAdminByEmail(String email);
}
