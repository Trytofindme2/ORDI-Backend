package com.example.ordi2.repo;


import com.example.ordi2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface userRepo extends JpaRepository<User, UUID>
{
    User findUserByEmail(String email);

    Optional<User> findUserById(UUID id);

    List<User> findUserByName(String name);

    int deleteUserById(UUID id);


}
