package com.example.ordi2.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ordi2.model.Comments;
import com.example.ordi2.model.Receipe;
@Repository
public interface CommentsRepo extends JpaRepository<Comments, UUID> {

	List<Comments> findByreceipe(Receipe receipe);

	Object findBycommentId(UUID deleteCommentid);

}
