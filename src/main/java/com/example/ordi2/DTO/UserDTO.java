package com.example.ordi2.DTO;

import com.example.ordi2.model.User;

import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String name;
    private String profile_URl;
    private String email;
    private String phoneNumber;
    private String bio;
    private String address;
    private String account_status;
    private String role;

    public UserDTO() {}

    // Constructor from User entity
    public UserDTO(UUID id, String name, String profile_URl, String email, String phoneNumber,
                   String bio, String address, String account_status, String role) {
        this.id = id;
        this.name = name;
        this.profile_URl = profile_URl;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.address = address;
        this.account_status = account_status;
        this.role = role;
    }

    public UserDTO(UUID id,  String name, String accountStatus) {

    }

    // Getters & Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getProfile_URl(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getBio(),
                user.getAddress(),
                user.getAccount_status(),
                user.getRole()
        );
    }

}
