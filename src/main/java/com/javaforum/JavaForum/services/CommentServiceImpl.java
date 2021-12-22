package com.javaforum.JavaForum.services;

import com.javaforum.JavaForum.exceptions.ResourceNotFoundException;
import com.javaforum.JavaForum.models.Comment;
import com.javaforum.JavaForum.models.Topic;
import com.javaforum.JavaForum.models.User;
import com.javaforum.JavaForum.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Transactional
@Service(value = "commentService")
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;


    @Override
    public List<Comment> findAllCommentsByTopic(Topic topic) {
        return commentRepository.findAllCommentsByTopic(topic);
    }

    @Transactional
    @Override
    public void deleteAllCommentsByTopic(Topic topic) {
        commentRepository.deleteAllCommentsByTopic(topic);
    }

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found!"));
    }

    @Transactional
    @Override
    public void delete(long id) {
        commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found!"));
        commentRepository.deleteById(id);
    }

    @Override
    public Comment save(long userid, long topicid, Comment comment) {
        Comment newComment = new Comment();
        if (comment.getCommentid()!= 0) {
            commentRepository.findById(comment.getCommentid()).orElseThrow(() ->
                    new ResourceNotFoundException("Comment id " + comment.getCommentid() + " not found!"));
            newComment.setCommentid(comment.getCommentid());
        }
        newComment.setCommentbody(comment.getCommentbody());
        newComment.setCommentphoto(comment.getCommentphoto());
        newComment.setCommentvideo(comment.getCommentvideo());
        newComment.setCommentgif(comment.getCommentgif());
        newComment.setTimepostedcomment(LocalDateTime.now());

        User userInfo = userService.findUserById(userid);
        newComment.setUser(userInfo);

        Topic topicInfo = topicService.findById(topicid);
        newComment.setTopic(topicInfo);

        return commentRepository.save(newComment);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll() { commentRepository.deleteAll();}

}
