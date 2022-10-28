package com.example.facebookbackendclone.request;

public class DeleteStoryRequest {

    private String storyId;

    public DeleteStoryRequest() {

    }

    public DeleteStoryRequest(String storyId) {
        this.storyId = storyId;
    }

    public int getStoryId() {
        return Integer.parseInt(storyId);
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }
}
