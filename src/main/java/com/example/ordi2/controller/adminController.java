package com.example.ordi2.controller;


import com.example.ordi2.DTO.ApiResUser;
import com.example.ordi2.DTO.UserUpdateRequest;
import com.example.ordi2.model.Admin;
import com.example.ordi2.model.User;
import com.example.ordi2.response.successMessage;
import com.example.ordi2.service.adminService;
import com.example.ordi2.response.LoginResponse;
import com.example.ordi2.response.errorMessage;
import com.example.ordi2.DTO.ApiResponse;
import com.example.ordi2.service.userService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/admin")
public class adminController
{

    private final adminService adminService;

    @Autowired
    public adminController(adminService adminService )
    {
        this.adminService = adminService;

    }

   @PostMapping("/create")
   public ResponseEntity<ApiResponse<Object>>create(@RequestBody Admin admin){
        try{
            Admin newAdmin = adminService.createAdmin(admin);
            ApiResponse<Object> response = new ApiResponse<>("success", newAdmin);
            return ResponseEntity.status(200).body(response);
        }catch (Exception e){
            ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(e.getMessage()));
            return ResponseEntity.status(401).body(response);
        }
   }

    @GetMapping("/getUserList")
    public ResponseEntity<ApiResUser<Object>> getUserList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (size <= 0) {
            List<User> users = adminService.getAllUsersSortedByName();
            ApiResUser<Object> response = new ApiResUser<>("success", users);
            return ResponseEntity.ok(response);
        } else {
            Page<User> userPage = adminService.getUsersPaginated(page, size);
            ApiResUser<Object> response = new ApiResUser<>("success", userPage);
            return ResponseEntity.ok(response);
        }
    }


    @PostMapping("/log-in")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody Admin admin ,  HttpServletResponse httpResponse) {
        try {
            LoginResponse loginResponse = adminService.adminLogIn(admin.getEmail(), admin.getPassword());
            Cookie cookie = new Cookie("admin-token" , loginResponse.getToken());
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            httpResponse.addCookie(cookie);
            ApiResponse<Object> response = new ApiResponse<>("success", loginResponse);
            return ResponseEntity.status(200).body(response);
        } catch (Exception exception) {
            ApiResponse<Object> response = new ApiResponse<>("Failed", new errorMessage(exception.getMessage()));
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/log-out")
    public ResponseEntity<String> adminLogOut(HttpServletResponse response) {
        Cookie cookie = new Cookie("admin-token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok("Admin logged out successfully");
    }

    @DeleteMapping("/deleteUserById/{userid}")
    public ResponseEntity<ApiResponse<Object>>deleteUserById(@PathVariable("userid")UUID userid)
    {
        try{
            boolean isDeleted = adminService.deleteUserById(userid);
            ApiResponse<Object> response = new ApiResponse<>("success", new successMessage("successfully deleted"));
            return ResponseEntity.status(200).body(response);
        }catch (Exception exception){
            ApiResponse<Object> response = new ApiResponse<>("failed", new errorMessage(exception.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/updateAccountStatus/{userid}")
    public ResponseEntity<ApiResponse<Object>> updateAccountStatus(
            @PathVariable("userid") UUID userid,
            @RequestBody User user) {
        try{
            String status = user.getAccount_status();
            User updateUser = adminService.updateUserStatus(userid, status );
            ApiResponse<Object> response = new ApiResponse<>("success", updateUser);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            ApiResponse<Object> response = new ApiResponse<>("failed", new errorMessage(exception.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
