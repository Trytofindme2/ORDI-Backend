package com.example.ordi2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String profile_URl;
    private String email;
    private String phoneNumber;
    private String password;
    private String bio;
    private String address;

    private String account_status = "Active";
    private String role = "User";


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Receipe> receipes;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.account_status = "Active";
        this.role = "User";
    }

    public User(String name, String profile_URl, String email, String phoneNumber, String password, String bio,
                String address, String account_status, String role,  List<Receipe> receipes) {
        this.name = name;
        this.profile_URl = profile_URl;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.bio = bio;
        this.address = address;
        this.account_status = account_status;
        this.role = role;
        this.receipes = receipes;
    }

    @PrePersist
    private void prePersistDefaults() {
        if (this.account_status == null) {
            this.account_status = "Active";
        }
        if (this.role == null) {
            this.role = "User";
        }
    }



    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_URl() {
        return profile_URl;
    }

    public void setProfile_URl(String profile_URl) {
        this.profile_URl = profile_URl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount_status() {
        return account_status;
    }

    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public List<Receipe> getReceipes() {
        return receipes;
    }

    public void setReceipes(List<Receipe> receipes) {
        this.receipes = receipes;
    }
}
