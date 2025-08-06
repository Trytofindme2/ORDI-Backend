package com.example.ordi2.service;

import com.example.ordi2.DTO.UserUpdateRequest;
import com.example.ordi2.helper.TokenGenerator;
import com.example.ordi2.helper.customException;

import com.example.ordi2.model.Admin;
import com.example.ordi2.model.User;
import com.example.ordi2.repo.adminRepo;
import com.example.ordi2.repo.userRepo;
import com.example.ordi2.response.LoginResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class userService
{
    private final userRepo userRepo;

    private TokenGenerator generator;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public userService(userRepo userRepo, adminRepo adminRepo , TokenGenerator generator ){
        this.userRepo = userRepo;
        this.generator = generator;
    }

    public User findUserByEmail(String email){
        User user = userRepo.findUserByEmail(email);
        if(user == null){
            throw new customException("User doesn't exit");
        }
        return user;
    }

    public LoginResponse userLogIn(String email , String password) {
       User user = userRepo.findUserByEmail(email);
       if(user != null){
           String dbPassword = user.getPassword();
           if(passwordEncoder.matches(password,dbPassword)){
               String token = generator.generateToken(email,user.getId());
               return  new LoginResponse(token,user);
           }else {
               throw new customException("Incorrect password. Please try again.");
           }
       }else {
           throw new customException("User account doesn't exit");
       }
    }

    public User createUser(String email, String password ) {
        User existingUser = userRepo.findUserByEmail(email);
        if (existingUser != null) {
            throw new customException("Email already exists");
        }
        String hashedPassword = passwordEncoder.encode(password);
        return userRepo.save(new User(email, hashedPassword));
    }

    public User updateUserInfo(UUID id, UserUpdateRequest userUpdateRequest, MultipartFile profileImage) throws IOException {
        Optional<User> existingUser = userRepo.findUserById(id);
        if (existingUser.isEmpty()) {
            throw new customException("User account doesn't exist");
        }
        User user = existingUser.get();

        if (userUpdateRequest.getName() != null) {
            user.setName(userUpdateRequest.getName());
        }
        if (userUpdateRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        }
        if (userUpdateRequest.getBio() != null) {
            user.setBio(userUpdateRequest.getBio());
        }
        if (userUpdateRequest.getAddress() != null) {
            user.setAddress(userUpdateRequest.getAddress());
        }

        if (profileImage != null && !profileImage.isEmpty()) {
            String uploadDir = "D:/ORDI2.0/src/main/resources/profile-images/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String originalFilename = profileImage.getOriginalFilename();
            String fileExtension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            }
            String filename = id.toString() + "_" + System.currentTimeMillis() + fileExtension;
            Path filePath = uploadPath.resolve(filename);
            profileImage.transferTo(filePath.toFile());
            user.setProfile_URl("/profile-images/" + filename);
        } else if (userUpdateRequest.getProfile_URl() != null) {
            user.setProfile_URl(userUpdateRequest.getProfile_URl());
        }

        return userRepo.save(user);
    }


}
