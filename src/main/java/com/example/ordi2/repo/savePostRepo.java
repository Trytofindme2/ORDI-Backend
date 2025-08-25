package com.example.ordi2.repo;

import com.example.ordi2.model.Receipe;
import com.example.ordi2.model.SavePosts;
import com.example.ordi2.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface savePostRepo extends JpaRepository<SavePosts , UUID>
{

    Optional<SavePosts> findByReceipeAndUser(Receipe receipe, User user);

    void deleteByReceipeAndUser(Receipe receipe, User user);

    boolean existsByReceipeAndUser(Receipe receipe, User user);

    List<SavePosts> findByUserId(UUID userId);

    @Query("SELECT COUNT(s) FROM SavePosts s WHERE s.receipe.id = :postId")
    long countByReceipeId(@Param("postId") UUID postId);
}
