package com.example.facebookbackendclone.response;

import com.example.facebookbackendclone.model.Story;
import com.example.facebookbackendclone.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> getNewUserResponse(HttpStatus status, Object data, String message) {
        Map<Object, Object> map = new LinkedHashMap<>(); // linkedHashMap to maintain insertion order
        map.put("status", status.value());
        map.put("userId", data);
        map.put("message", message);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> getAuthResponse(HttpStatus status, String message) {
        Map<Object, Object> map = new LinkedHashMap<>(); // linkedHashMap to maintain insertion order
        map.put("status", status.value());
        map.put("message", message);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> getLoginResponse(HttpStatus status, String token) {
        Map<Object, Object> map = new LinkedHashMap<>(); // linkedHashMap to maintain insertion order
        map.put("status", status.value());
        map.put("token", token);
//        map.put("roles", roles);


        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> getAdminResponse(HttpStatus status, String token, String message) {
        Map<Object, Object> map = new LinkedHashMap<>(); // linkedHashMap to maintain insertion order
        map.put("status", status.value());
        map.put("token", token);
        map.put("message", message);


        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> getUserResponse(HttpStatus status, User user) {
        Map<Object, Object> map = new LinkedHashMap<>(); // linkedHashMap to maintain insertion order
        map.put("status", status.value());
        map.put("userData", user);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> getAllUsersResponse(HttpStatus status, List<User> users) {
        Map<Object, Object> map = new LinkedHashMap<>(); // linkedHashMap to maintain insertion order
        map.put("status", status.value());
        map.put("users", users);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> getNewStoryResponse(HttpStatus status, String message, String storyId) {
        Map<Object, Object> map = new LinkedHashMap<>(); // linkedHashMap to maintain insertion order
        map.put("status", status.value());
        map.put("message", message);
        map.put("storyId", storyId);

        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> getStoriesResponse(HttpStatus status, List<Story> stories) {
        Map<Object, Object> map = new LinkedHashMap<>(); // linkedHashMap to maintain insertion order
        map.put("status", status.value());
        map.put("stories", stories);

        return new ResponseEntity<>(map, status);
    }

}
