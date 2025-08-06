package com.example.ordi2.DTO;

public class UserUpdateRequest
{
    private String name;
    private String bio;
    private String phoneNumber;
    private String profile_URl;
    private String address;

    public UserUpdateRequest(){};

    public UserUpdateRequest(String name, String bio, String phoneNumber, String profile_URl , String address) {
        this.name = name;
        this.bio = bio;
        this.phoneNumber = phoneNumber;
        this.profile_URl = profile_URl;
        this.address = address;
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
}
