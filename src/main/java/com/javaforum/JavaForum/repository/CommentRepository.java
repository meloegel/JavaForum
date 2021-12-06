package com.javaforum.JavaForum.repository;

import com.javaforum.JavaForum.models.Comment;
import org.springframework.data.repository.CrudRepository;


public interface CommentRepository extends CrudRepository<Comment, Long> {
}
