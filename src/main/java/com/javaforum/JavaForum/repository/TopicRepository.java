package com.javaforum.JavaForum.repository;

import com.javaforum.JavaForum.models.Topic;
import org.springframework.data.repository.CrudRepository;


public interface TopicRepository extends CrudRepository<Topic, Long> {
}
