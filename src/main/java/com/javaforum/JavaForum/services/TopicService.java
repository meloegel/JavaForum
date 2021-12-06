package com.javaforum.JavaForum.services;

import com.javaforum.JavaForum.models.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> findAll();

    Topic findByName(String topicname);

    Topic findById(long id);

    List<Topic> findByNameContaining(String topicname);

    void delete(long id);

    Topic save(long userid, Topic topic);

    Topic update(Topic topic, long id);

    void deleteAll();
}
