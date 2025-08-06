package com.example.ordi2.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResUser<T>
{
    private String message;

    @JsonProperty("users")
    private T users;

    public ApiResUser(String message, T users){
        this.message = message;
        this.users = users;
    }

    public T getUsers() {
        return users;
    }

    public void setUsers(T users) {
        this.users = users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
