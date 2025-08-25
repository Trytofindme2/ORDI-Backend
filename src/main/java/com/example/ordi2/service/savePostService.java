package com.example.ordi2.service;

import com.example.ordi2.DTO.SavedPostResponseDTO;
import com.example.ordi2.DTO.savePostDTO;
import com.example.ordi2.helper.customException;
import com.example.ordi2.model.Receipe;
import com.example.ordi2.model.SavePosts;
import com.example.ordi2.model.User;
import com.example.ordi2.repo.receipeRepo;
import com.example.ordi2.repo.savePostRepo;
import com.example.ordi2.repo.userRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class savePostService
{
    private final savePostRepo savePostRepo;
    private final userRepo userRepo;
    private final receipeRepo receipeRepo;

    public savePostService(savePostRepo savePostRepo , userRepo userRepo ,  receipeRepo receipeRepo){
        this.savePostRepo = savePostRepo;
        this.receipeRepo =receipeRepo;
        this.userRepo = userRepo;
    }

    public boolean savePost(UUID postId, UUID userId) {
        Receipe receipe = receipeRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (savePostRepo.existsByReceipeAndUser(receipe, user)) {
            return false; // already saved
        }

        SavePosts savePost = new SavePosts(user, receipe);
        savePostRepo.save(savePost);
        return true;
    }

    public boolean unsavePost(UUID postId, UUID userId) {
        Receipe receipe = receipeRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<SavePosts> savePost = savePostRepo.findByReceipeAndUser(receipe, user);
        if (savePost.isPresent()) {
            savePostRepo.delete(savePost.get());
            return true;
        }
        return false;
    }

    public boolean isSaved(UUID postId, UUID userId) {
        Receipe receipe = receipeRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return savePostRepo.existsByReceipeAndUser(receipe, user);
    }

    public List<SavedPostResponseDTO> getSavedPostsByUser(UUID userId) {
        List<SavePosts> savedPosts = savePostRepo.findByUserId(userId);

        return savedPosts.stream().map(sp -> {
            var receipe = sp.getReceipe();
            var author = receipe.getUser();
            return new SavedPostResponseDTO(
                    sp.getId(),
                    receipe.getId(),
                    receipe.getTitle(),
                    receipe.getDescription(),
                    receipe.getDifficulty(),
                    receipe.getIngredients(),
                    receipe.getPreparationTime(),
                    receipe.getCookingTime(),
                    receipe.getImageUrls(),
                    author.getName(),
                    author.getProfile_URl(),
                    receipe.getPostAt(),
                    sp.getSavedAt()
            );
        }).collect(Collectors.toList());
    }

    public long getSaveCountForPost(UUID postId) {
        return savePostRepo.countByReceipeId(postId);
    }
}
