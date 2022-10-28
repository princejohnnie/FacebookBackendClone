package com.example.facebookbackendclone.request;

public class GetUserRequest {
    private String userId;

    public int getUserId() {
        return Integer.parseInt(userId);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
