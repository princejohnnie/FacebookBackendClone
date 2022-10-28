package com.example.facebookbackendclone.request;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.JoinColumn;

public class LogoutUserRequest {

    private String email;

    public LogoutUserRequest() {

    }

    public LogoutUserRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
