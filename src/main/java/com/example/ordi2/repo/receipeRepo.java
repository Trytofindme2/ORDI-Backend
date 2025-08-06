package com.example.ordi2.repo;

import com.example.ordi2.model.Receipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface receipeRepo extends JpaRepository<Receipe, UUID>
{
    List<Receipe> findByUser_Id(UUID userId);

    List<Receipe> findAllByOrderByPostAtDesc();
}
