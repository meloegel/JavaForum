package com.javaforum.JavaForum.controllers;

import com.javaforum.JavaForum.models.Topic;
import com.javaforum.JavaForum.models.User;
import com.javaforum.JavaForum.services.CommentService;
import com.javaforum.JavaForum.services.TopicService;
import com.javaforum.JavaForum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    // Returns a list of all topics
    // Link: http://localhost:2019/topics/topics
    @GetMapping(value = "/topics", produces = "application/json")
    public ResponseEntity<?> listAllTopics(){
        List<Topic> allTopics = topicService.findAll();
        return new ResponseEntity<>(allTopics, HttpStatus.OK);
    }

    // Returns a list of all topics for a valid user
    // Link: http://localhost:2019/topics/4
    // @param userid - id of user you wish to return all topics for
    @GetMapping(value = "/topics/{userid}", produces = "application/json")
    public ResponseEntity<?> listAllTopicsByUser(@PathVariable Long userid) {
        User user = userService.findUserById(userid);
        List<Topic> allTopicsByUser = topicService.findAllTopicsByUser(user);
        return new ResponseEntity<>(allTopicsByUser, HttpStatus.OK);
    }

    // Return a Topic object based on a given topicname
    // Link: http://localhost:2019/topics/topic/name/cinnamon
    // @param topicname - Name of the topic you wish to find
    @GetMapping(value="/topic/name/{topicname}", produces = "application/json")
    public ResponseEntity<?> getTopicByName(@PathVariable String topicname) {
        Topic topic = topicService.findByName(topicname);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    // Returns single topic based off id
    // Link: http://localhost:2019/topics/topic/4
    @GetMapping(value="/topic/{topicid}", produces = "application/json")
    public ResponseEntity<?> getByTopicid(@PathVariable Long topicid){
        Topic topic = topicService.findById(topicid);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    // Returns a list of topics whose topicname contains the given substring
    // Link: http://localhost:2019/topics/topic/name/like/ci
    @GetMapping(value = "/topics/topic/like/{topicname}", produces = "application/json")
    public ResponseEntity<?> getTopicLikeName(@PathVariable String topicname) {
        List<Topic> topic = topicService.findByNameContaining(topicname);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    // Given a complete Topic Object, create a new Topic record.
    // http://localhost:2019/topics/4/topic
    // @param newTopic - A complete new topic to add.
    // @param userid - id of user posting topic
    // @return A location header with the URI to the newly created topic and a status of CREATED
    @PostMapping(value = "/{userid}/topic", consumes = "application/json")
    public ResponseEntity<?> addNewTopic(@PathVariable long userid, @RequestBody Topic newTopic) throws URISyntaxException {
        newTopic.setTopicid(0);
        newTopic = topicService.save(userid, newTopic);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{topicid}")
                .buildAndExpand(newTopic.getTopicid())
                .toUri();
        responseHeaders.setLocation(newUserURI);
        return new ResponseEntity<>(newTopic, responseHeaders, HttpStatus.CREATED);
    }

    // Given a complete Topic Object, Given the topicid, primary key, is in the Topic table,
    // replace the Topic record
    // Link: http://localhost:2019/topics/topic/15
    // @param updateTopic - A complete Topic object
    // @param topicid     The primary key of the topic you wish to replace.
    @PutMapping(value = "/topic/{topicid}", consumes = "application/json")
    public ResponseEntity<?> updateFullTopic( @RequestBody Topic updatedTopic, @PathVariable long topicid) {
        updatedTopic.setTopicid(topicid);
        topicService.save(topicid, updatedTopic);
        return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
    }

    // Updates the topic record associated with the given id with the provided data.
    //       Only the provided fields are affected.
    // Link: http://localhost:2019/topics/topic/7
    // @param updateTopic - An object containing values for just the fields that are being updated.
    //      All other fields are left NULL.
    // @param topicid - The primary key of the topic you wish to update.
    @PatchMapping(value = "/topic/{topicid}", consumes = "application/json")
    public ResponseEntity<?> updateTopic(@RequestBody Topic updateTopic, @PathVariable long topicid) {
        topicService.update(updateTopic, topicid);
        return new ResponseEntity<>(updateTopic, HttpStatus.OK);
    }

    // Deletes a given topic and its comments
    // Link: http://localhost:2019/topics/topic/14
    // @param topicid - The primary key of the topic you wish to delete
    @DeleteMapping(value = "/topic/{topicid}")
    public ResponseEntity<?> deleteTopicById(@PathVariable long topicid) {
        Topic topic = topicService.findById(topicid);
        commentService.deleteAllCommentsByTopic(topic);
        topicService.delete(topicid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
