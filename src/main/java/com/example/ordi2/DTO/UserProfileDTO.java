package com.example.ordi2.DTO;

import java.util.List;
import java.util.Set;

import com.example.ordi2.model.User;

public class UserProfileDTO {
    private String message;
    private Set<UserDTO> followers;
    private Set<UserDTO> followings;
    private Set<UserDTO> friends;

    public UserProfileDTO(String message, Set<UserDTO> followers, Set<UserDTO> followings, Set<UserDTO> friends) {
        this.message = message;
        this.followers = followers;
        this.followings = followings;
        this.friends = friends;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Set<UserDTO> getFollowers() { return followers; }
    public void setFollowers(Set<UserDTO> followers) { this.followers = followers; }

    public Set<UserDTO> getFollowings() { return followings; }
    public void setFollowings(Set<UserDTO> followings) { this.followings = followings; }

    public Set<UserDTO> getFriends() { return friends; }
    public void setFriends(Set<UserDTO> friends) { this.friends = friends; }
}
