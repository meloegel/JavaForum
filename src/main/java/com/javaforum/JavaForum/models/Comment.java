package com.javaforum.JavaForum.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "comments")
public class Comment extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentid;

    @Column(nullable = false)
    private String commentbody;

    @Column
    private String commentphoto;

    @Column
    private String commentvideo;

    @Column
    private String commentgif;

    @Column(nullable = false)
    private LocalDateTime timepostedcomment;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = {"topics", "roles"}, allowSetters = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "topicid", nullable = false)
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    private Topic topic;

    public Comment() {}

    public Comment(String commentbody, User user, Topic topic) {
        this.commentbody = commentbody;
        this.user = user;
        this.topic = topic;
    }

    public Comment(String commentbody, String commentphoto, String commentvideo, String commentgif, LocalDateTime timepostedcomment, User user, Topic topic) {
        this.commentbody = commentbody;
        this.commentphoto = commentphoto;
        this.commentvideo = commentvideo;
        this.commentgif = commentgif;
        this.timepostedcomment = timepostedcomment;
        this.user = user;
        this.topic = topic;
    }

    public long getCommentid() {
        return commentid;
    }

    public void setCommentid(long commentid) {
        this.commentid = commentid;
    }

    public String getCommentbody() {
        return commentbody;
    }

    public void setCommentbody(String commentbody) {
        this.commentbody = commentbody;
    }

    public String getCommentphoto() {
        return commentphoto;
    }

    public void setCommentphoto(String commentphoto) {
        this.commentphoto = commentphoto;
    }

    public String getCommentvideo() {
        return commentvideo;
    }

    public void setCommentvideo(String commentvideo) {
        this.commentvideo = commentvideo;
    }

    public String getCommentgif() {
        return commentgif;
    }

    public void setCommentgif(String commentgif) {
        this.commentgif = commentgif;
    }

    public LocalDateTime getTimepostedcomment() {
        return timepostedcomment;
    }

    public void setTimepostedcomment(LocalDateTime timepostedcomment) {
        this.timepostedcomment = timepostedcomment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
