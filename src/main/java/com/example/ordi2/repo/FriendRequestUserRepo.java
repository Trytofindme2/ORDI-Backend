package com.example.ordi2.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ordi2.model.FriendRequestUser;
import com.example.ordi2.model.User;

@Repository
public interface FriendRequestUserRepo extends JpaRepository<FriendRequestUser, UUID> {

	List<FriendRequestUser> findByReceiver(User user);

	List<FriendRequestUser> findBySender(User user);

	Optional<FriendRequestUser> findByReceiverAndSender(User receiver, User sender);

}
