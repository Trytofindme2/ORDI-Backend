package com.example.ordi2.repo;

import com.example.ordi2.model.Receipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface receipeRepo extends JpaRepository<Receipe, UUID>
{
    List<Receipe> findByUser_Id(UUID userId);

    List<Receipe> findAllByOrderByPostAtDesc();

    @Query("SELECT r FROM Receipe r " +
            "LEFT JOIN FETCH r.reports rep " +
            "LEFT JOIN FETCH rep.reportedBy " +
            "WHERE r.id = :id")
    Optional<Receipe> findByIdWithReportsAndUsers(@Param("id") UUID id);
}
