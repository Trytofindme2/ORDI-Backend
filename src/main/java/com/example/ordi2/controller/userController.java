package com.example.ordi2.controller;

import com.example.ordi2.DTO.ApiResponse;
import com.example.ordi2.DTO.ReceipeDTO;
import com.example.ordi2.DTO.UserUpdateRequest;
import com.example.ordi2.DTO.VerificationRequest;
import com.example.ordi2.model.Receipe;
import com.example.ordi2.model.User;
import com.example.ordi2.response.LoginResponse;
import com.example.ordi2.response.errorMessage;
import com.example.ordi2.response.successMessage;
import com.example.ordi2.service.emailService;
import com.example.ordi2.service.receipeService;
import com.example.ordi2.service.userService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class userController
{

    private final userService userService;
    private final emailService emailService;
    private final receipeService receipeService;

    public userController(userService userService , emailService emailService , receipeService receipeService){
        this.userService = userService;
        this.emailService = emailService;
        this.receipeService = receipeService;
    }

    @GetMapping("/start")
    public ResponseEntity<ApiResponse<Object>> startup(){
        ApiResponse<Object> response = new ApiResponse<>("success", new successMessage("welcome to start up"));
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/log-in")
    public ResponseEntity<ApiResponse<Object>>LogIn(@RequestBody User user , HttpServletResponse httpServletResponse){
        try{
            LoginResponse loginResponse = userService.userLogIn(user.getEmail(), user.getPassword());
            Cookie cookie = new Cookie("user-token" , loginResponse.getToken());
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            httpServletResponse.addCookie(cookie);
            ApiResponse<Object> response = new ApiResponse<>("success", loginResponse);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
           ApiResponse<Object>response = new ApiResponse<>("Failed",new errorMessage(e.getMessage()));
            return ResponseEntity.status(401).body(response);
        }
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<ApiResponse<Object>> findByEmail(@PathVariable("email")String email){
        try{
            User user = userService.findUserByEmail(email);
            ApiResponse<Object> response = new ApiResponse<>("success", user);
            return ResponseEntity.status(200).body(response);
        }
        catch (Exception e){
            ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(e.getMessage()));
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/sendVerificationCode")
    public ResponseEntity<ApiResponse<Object>> sendVerificationCode(@RequestBody User user) {
        try {
            String generatedCode = emailService.generateCode();
            emailService.saveCode(user.getEmail(), generatedCode);
            emailService.sendVerificationCode(user.getEmail(), generatedCode);
            ApiResponse<Object> response = new ApiResponse<>("success", new successMessage("Email already send to your" + " " + user.getEmail()));
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(e.getMessage()));
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/verified")
    public ResponseEntity<ApiResponse<Object>>verified(@RequestBody VerificationRequest verificationRequest) {
        try{
            boolean isVerified = emailService.verifyCode(verificationRequest.getEmail(),verificationRequest.getCode());
            if (isVerified) {
                User user = userService.createUser(verificationRequest.getEmail(), verificationRequest.getPassword());
                ApiResponse<Object> response = new ApiResponse<>("success", user);
                return ResponseEntity.status(200).body(response);
            } else {
                ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage("Invalid verification code"));
                return ResponseEntity.status(400).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(e.getMessage()));
            return ResponseEntity.status(500).body(response);
        }
    }

    @PatchMapping(value = "/editProfile/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Object>> editProfile(
            @PathVariable("id") UUID id,
            @RequestPart("userUpdateRequest") UserUpdateRequest userUpdateRequest,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) throws IOException {

        User user = userService.updateUserInfo(id, userUpdateRequest, profileImage);
        ApiResponse<Object> response = new ApiResponse<>("success", user);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/createReceipe/{id}")
    public ResponseEntity<ApiResponse<Object>>createReceipe(@PathVariable("id")UUID id , @RequestBody Receipe receipe)
    {
        Receipe createReceipe = receipeService.createReceipe(id,receipe);
        ApiResponse<Object>response = new ApiResponse<>("success" , createReceipe);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/getReceipeByUserId/{id}")
    public ResponseEntity<ApiResponse<?>>findReceipeByUserId(@PathVariable("id")UUID id){
        List<Receipe> receipeList = receipeService.findReceipeByUserId(id);
        ApiResponse<Object>response = new ApiResponse<>("success" , receipeList);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/log-out")
    public ResponseEntity<String> adminLogOut(HttpServletResponse response) {
        Cookie cookie = new Cookie("user-token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok("User logged out successfully");
    }

    @GetMapping("/getPosts")
    public ResponseEntity<ApiResponse<?>>getAllPost(){
        List<ReceipeDTO>receipeList = receipeService.getAllReceipePost();
        ApiResponse<Object>response = new ApiResponse<>("success" , receipeList);
        return ResponseEntity.status(200).body(response);
    }


}
