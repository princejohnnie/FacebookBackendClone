package com.example.facebookbackendclone.controller;

import com.example.facebookbackendclone.model.*;
import com.example.facebookbackendclone.request.DeleteStoryRequest;
import com.example.facebookbackendclone.request.GetStoriesRequest;
import com.example.facebookbackendclone.response.ResponseHandler;
import com.example.facebookbackendclone.service.MyUserDetails;
import com.example.facebookbackendclone.service.StoryService;
import com.example.facebookbackendclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facebook/user")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('AUTHENTICATED')")
    @PostMapping("/story")
    public ResponseEntity<Object> createStory(@RequestBody Story story) {
        try {
            try {
                MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
                String email = userDetails.getUsername();
                User user = userService.findByEmail(email);
                story.setUserId(user.getId());

            } catch (Exception e) {
                return ResponseHandler.getAuthResponse(HttpStatus.UNAUTHORIZED, "Please login to create story");
            }

            Story newStory = storyService.createStory(story);

            return ResponseHandler.getNewStoryResponse(HttpStatus.OK, "Story created successfully", String.valueOf(newStory.getId()));
        } catch (Exception e) {
            return ResponseHandler.getAuthResponse(HttpStatus.BAD_REQUEST, "Cannot create story because " + e.getMessage());
        }

    }

    @PreAuthorize("hasRole('AUTHENTICATED')")
    @DeleteMapping("/story")
    public ResponseEntity<Object> deleteStory(@RequestBody DeleteStoryRequest deleteStoryRequest) {
        try {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String email = userDetails.getUsername();

            User user = userService.findByEmail(email);

            Story story = storyService.getStoryById(deleteStoryRequest.getStoryId());

            if (user.getId() == story.getUserId()) {
                storyService.deleteById(deleteStoryRequest.getStoryId());
                return ResponseHandler.getAuthResponse(HttpStatus.OK, "Story deleted successfully");
            } else {
                return ResponseHandler.getAuthResponse(HttpStatus.UNAUTHORIZED, "User not authorized to delete story");
            }

        } catch (Exception e) {
            return ResponseHandler.getAuthResponse(HttpStatus.BAD_REQUEST, "Please login to create story");
        }
    }

    @PreAuthorize("hasRole('AUTHENTICATED')")
    @GetMapping("/stories")
    public ResponseEntity<Object> getAllStories(@RequestBody GetStoriesRequest request) {
        try {
            MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String email = userDetails.getUsername();

            User user = userService.findByEmail(email);

            int start;
            if (request.getStart() == 0) {
                start = request.getStart();
            } else {
                start = request.getStart() - 1;
            }


            List<Story> stories = storyService.getAllStories(user.getId(),  request.getCount(), start);

            return ResponseHandler.getStoriesResponse(HttpStatus.OK, stories);
        } catch (Exception e) {
            return ResponseHandler.getAuthResponse(HttpStatus.NOT_FOUND, "Could not fetch stories because " + e.getMessage());
        }

    }


}
