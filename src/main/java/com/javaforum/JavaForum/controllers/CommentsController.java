package com.javaforum.JavaForum.controllers;

import com.javaforum.JavaForum.models.Comment;
import com.javaforum.JavaForum.models.Topic;
import com.javaforum.JavaForum.services.CommentService;
import com.javaforum.JavaForum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private TopicService topicService;


    // Returns a list of all comments for topic, given valid topic id
    // Link: http://localhost:2019/comments/comments/5
    // @param topicid - the id of the topic that you wish to retrieve comments for
    @GetMapping(value = "/comments/{topicid}", produces = "application/json")
    public ResponseEntity<?> listAllCommentsForTopic(@PathVariable long topicid){
        Topic topic = topicService.findById(topicid);
        List<Comment> allComments = commentService.findAllCommentsByTopic(topic);
        return new ResponseEntity<>(allComments, HttpStatus.OK);
    }
}
