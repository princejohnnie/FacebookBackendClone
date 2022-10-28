package com.example.facebookbackendclone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String heading;

    private String body;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private StoryType type;

    @Column(name = "shareWith")
    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    private List<String> shareWith = new ArrayList<>();

    @JsonIgnore
    private Integer userId;

    public Story() {

    }

    public Story(int id, String heading, String body, StoryType type, List<String> shareWith, Integer userId) {
        this.id = id;
        this.heading = heading;
        this.body = body;
        this.type = type;
        this.shareWith = shareWith;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public StoryType getType() {
        return type;
    }

    public void setType(StoryType type) {
        this.type = type;
    }

    public List<String> getShareWith() {
        return shareWith;
    }

    public void setShareWith(List<String> shareWith) throws Exception {
        if (type.name().equalsIgnoreCase("PRIVATE")) {
            throw new Exception("Cannot share a Private story");
        }
        this.shareWith = shareWith;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}


