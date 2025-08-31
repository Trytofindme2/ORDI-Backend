package com.example.ordi2.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordi2.model.Comments;
import com.example.ordi2.model.Receipe;
import com.example.ordi2.repo.CommentsRepo;

import jakarta.transaction.Transactional;

@Service
public class CommentsService {
	@Autowired
	private CommentsRepo commentsRepo;

	public List<Comments> getCommentsByReceipe(Receipe receipe) {
		// TODO Auto-generated method stub
		return commentsRepo.findByreceipe(receipe);
	}

	public Comments save(Comments comment) {
		// TODO Auto-generated method stub
		return commentsRepo.save(comment);
	}

	@Transactional
    public boolean deleteCommentById(UUID deleteCommentId) {
        if (commentsRepo.existsById(deleteCommentId)) {
            commentsRepo.deleteById(deleteCommentId);
            return true; // Successfully deleted
        }
        return false; // Comment not found
    }

	public Object getCommentsById(UUID deleteCommentid) {
		// TODO Auto-generated method stub
		return commentsRepo.findBycommentId(deleteCommentid);
	}

}
