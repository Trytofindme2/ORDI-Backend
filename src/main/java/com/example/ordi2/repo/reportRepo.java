package com.example.ordi2.repo;

import com.example.ordi2.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

@Repository
public interface reportRepo extends JpaRepository<Report , UUID>
{
    Page<Report> findAll(Pageable pageable);
}
