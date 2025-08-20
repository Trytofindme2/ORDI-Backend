package com.example.ordi2.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class SavePosts
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Receipe receipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "saved_at", updatable = false)
    private LocalDateTime savedAt;

    public SavePosts(User user, Receipe receipe) {
        this.user = user;
        this.receipe = receipe;
    }

    @PrePersist
    public void setPostAtBeforePersist() {
        this.savedAt = LocalDateTime.now();
    }

    public SavePosts(){};

    public SavePosts(Receipe receipe, User user, LocalDateTime savedAt) {
        this.receipe = receipe;
        this.user = user;
        this.savedAt = savedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Receipe getReceipe() {
        return receipe;
    }

    public void setReceipe(Receipe receipe) {
        this.receipe = receipe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
