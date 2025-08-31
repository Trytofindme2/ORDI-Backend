package com.example.ordi2.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ordi2.model.ChatMessage;
import com.example.ordi2.model.User;
@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessage, UUID> {

	List<ChatMessage> findBySenderEmailOrReceiverEmailOrderBySentAtAsc(String userEmail, String userEmail2);

	@Query("SELECT m FROM ChatMessage m WHERE " +
		       "(m.sender = :user1 AND m.receiver = :user2) OR " +
		       "(m.sender = :user2 AND m.receiver = :user1) " +
		       "ORDER BY m.sentAt ASC")
		List<ChatMessage> findChatMessagesBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);

	ChatMessage findBymessageId(UUID messageId);

}
