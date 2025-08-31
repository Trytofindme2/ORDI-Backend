package com.example.ordi2.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ordi2.model.Reaction;
import com.example.ordi2.model.Receipe;
import com.example.ordi2.model.User;

@Repository
public interface ReactionRepo extends JpaRepository<Reaction, UUID> {

	Optional<Reaction> findByUserAndReceipe(User user, Receipe receipe);

}
