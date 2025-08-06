package com.example.ordi2.service;

import com.example.ordi2.DTO.ReceipeDTO;
import com.example.ordi2.model.Receipe;
import com.example.ordi2.model.User;
import com.example.ordi2.repo.receipeRepo;
import com.example.ordi2.repo.userRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class receipeService
{
    private final receipeRepo receipeRepo;
    private final userRepo userRepo;

    public receipeService(receipeRepo receipeRepo , userRepo userRepo){
        this.receipeRepo = receipeRepo;
        this.userRepo = userRepo;
    }

    public Receipe createReceipe(UUID userId, Receipe receipe) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        receipe.setUser(user);
        return receipeRepo.save(receipe);
    }

    public List<Receipe> findReceipeByUserId(UUID userid){
        return receipeRepo.findByUser_Id(userid);
    }

    public List<ReceipeDTO> getAllReceipePost() {
        List<Receipe> receipes = receipeRepo.findAllByOrderByPostAtDesc(); // Sorted here
        return receipes.stream().map(r -> {
            User u = r.getUser();
            return new ReceipeDTO(
                    r.getId(),
                    r.getTitle(),
                    r.getDescription(),
                    r.getDifficulty(),
                    r.getIngredients(),
                    r.getPreparationTime(),
                    r.getCookingTime(),
                    r.getPostAt(),
                    r.getImageUrls(),
                    u != null ? u.getId() : null,
                    u != null ? u.getName() : null,
                    u != null ? u.getProfile_URl() : null
            );
        }).collect(Collectors.toList());
    }



}
