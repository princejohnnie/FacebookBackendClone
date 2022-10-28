package com.example.facebookbackendclone.repository;

import com.example.facebookbackendclone.model.Story;
import org.hibernate.cfg.JPAIndexHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {

    String QUERY = "SELECT * FROM stories WHERE user_id = ?1 ORDER BY id LIMIT ?2 OFFSET ?3";

    @Query(value = QUERY, nativeQuery = true)
    List<Story> findAllStoriesBy(Integer userId, int count, int start);
}
