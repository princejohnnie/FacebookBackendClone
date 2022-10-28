package com.example.facebookbackendclone.service;

import com.example.facebookbackendclone.model.Story;
import com.example.facebookbackendclone.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryService {

    @Autowired
    private StoryRepository repository;

    public Story createStory(Story story) {
        return repository.save(story);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }


    public List<Story> getAllStories(Integer userId, int count, int start) {
        return repository.findAllStoriesBy(userId, count, start);
    }

    public Story getStoryById(int id) {
        return repository.findById(id).orElse(null);
    }
}
