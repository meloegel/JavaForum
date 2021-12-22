package com.javaforum.JavaForum.controllers;

import com.javaforum.JavaForum.models.Comment;
import com.javaforum.JavaForum.models.Topic;
import com.javaforum.JavaForum.services.CommentService;
import com.javaforum.JavaForum.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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

    // Deletes all comments for a topic, given a valid topicid
    // Link: http://localhost:2019/comments/comments/10
    // @param topicid - the id of the topic that you wish to delete all comments for
    @DeleteMapping(value = "/comments/{topicid}")
    public ResponseEntity<?> deleteAllCommentsForTopic(@PathVariable long topicid){
        Topic topic = topicService.findById(topicid);
        commentService.deleteAllCommentsByTopic(topic);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Returns single comment based off id
    // Link: http://localhost:2019/comments/comment/4
    @GetMapping(value="/comment/{commentid}", produces = "application/json")
    public ResponseEntity<?> getByCommentid(@PathVariable Long commentid){
        Comment comment = commentService.findById(commentid);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    // Given a complete Comment Object, create a new Comment record.
    // http://localhost:2019/comments/4/7/comment
    // @param newComment - A complete new comment to add.
    // @param userid - id of user posting comment.
    // @param topicid - id of topic the comment is for.
    // @return A location header with the URI to the newly created comment and a status of CREATED
    @PostMapping(value = "/{userid}/{topicid}/comment", consumes = "application/json")
    public ResponseEntity<?> addNewComment(@PathVariable long userid,
                                           @PathVariable long topicid,
                                           @Valid @RequestBody Comment newComment)
            throws URISyntaxException {
        newComment.setCommentid(0);
        newComment = commentService.save(userid, topicid, newComment);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/comment/{commentid}")
                .buildAndExpand(newComment.getCommentid())
                .toUri();
        responseHeaders.setLocation(newUserURI);
        return new ResponseEntity<>(newComment, responseHeaders, HttpStatus.CREATED);
    }

    // Deletes a given commnent
    // Link: http://localhost:2019/comments/comment/14
    // @param commentid - The primary key of the comment you wish to delete
    @DeleteMapping(value = "/comment/{commentid}")
    public ResponseEntity<?> deleteCommentById(@PathVariable long commentid) {
        commentService.delete(commentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
