package com.example.facebookbackendclone.request;

public class DeleteUserRequest {
    private String userId;
    private String email;


    public DeleteUserRequest(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public Integer getUserId() {
        return Integer.parseInt(userId);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
