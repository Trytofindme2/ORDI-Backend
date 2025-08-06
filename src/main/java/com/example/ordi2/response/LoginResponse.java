package com.example.ordi2.response;

import com.example.ordi2.model.Admin;
import com.example.ordi2.model.User;

public class LoginResponse {

    private String token;
    private Admin admin;
    private User user;

    public LoginResponse(String token, Admin admin) {
        this.token = token;
        this.admin = admin;
    }

    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public Admin getAdmin() {
        return admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
