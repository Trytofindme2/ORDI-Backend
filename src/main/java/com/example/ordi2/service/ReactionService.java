package com.example.ordi2.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordi2.model.Reaction;
import com.example.ordi2.model.Receipe;
import com.example.ordi2.model.User;
import com.example.ordi2.repo.ReactionRepo;

@Service
public class ReactionService {
	@Autowired
	private ReactionRepo reactionRepo;
	
	public Reaction addOrUpdateReaction(User user, Receipe receipe, int reactioncount) {
	    Optional<Reaction> existingReaction = reactionRepo.findByUserAndReceipe(user, receipe);

	    if (existingReaction.isPresent()) {
	        Reaction reaction = existingReaction.get();

	        // If user already reacted with same value → remove it (toggle off)
	        if (reaction.getReactioncount() == reactioncount) {
	            reactionRepo.delete(reaction);
	            return null; // no reaction now
	        }

	        // Else update to new reaction value
	        reaction.setReactioncount(reactioncount);
	        return reactionRepo.save(reaction);
	    } else {
	        // Create new reaction
	        Reaction newReaction = new Reaction(user, receipe, reactioncount);
	        return reactionRepo.save(newReaction);
	    }
	}


}
