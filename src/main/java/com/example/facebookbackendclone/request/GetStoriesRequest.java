package com.example.facebookbackendclone.request;

import com.fasterxml.jackson.annotation.JsonValue;

public class GetStoriesRequest {


    private Integer count;
    private Integer start;


    public GetStoriesRequest(Integer count, Integer start) {
        this.count = count;
        this.start = start;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
