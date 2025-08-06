package com.example.ordi2.service;

import com.example.ordi2.DTO.UserUpdateRequest;
import com.example.ordi2.model.User;
import com.example.ordi2.repo.adminRepo;
import com.example.ordi2.model.Admin;
import com.example.ordi2.helper.*;
import com.example.ordi2.repo.userRepo;
import com.example.ordi2.response.LoginResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class adminService {

    private final adminRepo adminRepo;

    private final userRepo userRepo;

    private TokenGenerator generator ;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public adminService(adminRepo adminRepo , TokenGenerator generator , userRepo userRepo) {
        this.adminRepo = adminRepo;
        this.generator = generator;
        this.userRepo = userRepo;
    }

    public boolean findByEmail(String email){
        Optional<Admin> admin = adminRepo.findAdminByEmail(email);
        return admin.isPresent();
    }

    public LoginResponse adminLogIn(String email, String password) {
        Optional<Admin> adminOptional = adminRepo.findAdminByEmail(email);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (passwordEncoder.matches(password, admin.getPassword())) {
                String token = generator.generateToken(admin.getEmail(), admin.getId());
                return new LoginResponse(token, admin);
            } else {
                throw new customException("Incorrect password. Please try again.");
            }
        } else {
            throw new customException("Admin account not found.");
        }
    }

    public Page<User> getUsersPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return userRepo.findAll(pageable);
    }

    public Admin createAdmin(Admin admin)
    {
        if(findByEmail(admin.getEmail())){
            throw new customException("Admin account already exists.");
        }
        else{
            String encryptPassword = passwordEncoder.encode(admin.getPassword());
            admin.setPassword(encryptPassword);
            return adminRepo.save(admin);
        }
    }

    public List<User> getAllUsersSortedByName() {
        return userRepo.findAll(Sort.by("name").ascending());
    }

    @Transactional
    public boolean deleteUserById(UUID userid){
        int EffectedRow =  userRepo.deleteUserById(userid);
        if(EffectedRow == 0){
            throw new customException("cannot delete user");
        }
        return true;
    }

    public User updateUserStatus(UUID userid, String status) {
        User user = userRepo.findUserById(userid)
                .orElseThrow(() -> new customException("User Account doesn't exist"));
        user.setAccount_status(status);
        userRepo.save(user);
        return  user ;
    }

    
}
