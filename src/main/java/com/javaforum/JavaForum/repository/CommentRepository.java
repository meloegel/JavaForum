package com.javaforum.JavaForum.repository;

import com.javaforum.JavaForum.models.Comment;
import com.javaforum.JavaForum.models.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAllCommentsByTopic(Topic topic);
}
