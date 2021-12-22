package com.javaforum.JavaForum.services;

import com.javaforum.JavaForum.models.Comment;
import com.javaforum.JavaForum.models.Topic;

import java.util.List;


public interface CommentService {
    List<Comment> findAllCommentsByTopic(Topic topic);

    Comment findById(long id);

    void delete(long id);

    Comment save(long userid, long topicid, Comment comment);

    void deleteAll();

    void deleteAllCommentsByTopic(Topic topic);
}
