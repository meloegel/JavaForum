package com.javaforum.JavaForum.controllers;


import com.javaforum.JavaForum.models.Topic;
import com.javaforum.JavaForum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    // Returns a list of all topics
    // Link: http://localhost:2019/topics/topics
    @GetMapping(value = "/topics", produces = "application/json")
    public ResponseEntity<?> listAllTopics(){
        List<Topic> allTopics = topicService.findAll();
        return new ResponseEntity<>(allTopics, HttpStatus.OK);
    }
}
