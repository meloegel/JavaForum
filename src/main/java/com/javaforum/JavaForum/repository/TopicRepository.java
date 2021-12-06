package com.javaforum.JavaForum.repository;

import com.javaforum.JavaForum.models.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TopicRepository extends CrudRepository<Topic, Long> {
    Topic findByTopicname(String topicname);

    List<Topic> findByTopicnameContainingIgnoreCase(String topicname);
}
