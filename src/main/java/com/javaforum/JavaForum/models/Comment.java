package com.javaforum.JavaForum.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "comments")
public class Comment extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long comment_id;

    @Column(nullable = false)
    private String comment_body;

    @Column
    private String comment_photo;

    @Column
    private String comment_video;

    @Column
    private String comment_gif;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = {"topics", "roles"}, allowSetters = true)
    private User user;

    @OneToMany(mappedBy = "topic_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "comment_id", allowSetters = true)
    private Topic topic;

    public Comment() {}

    public Comment(String comment_body, User user, Topic topic) {
        this.comment_body = comment_body;
        this.user = user;
        this.topic = topic;
    }

    public Comment(String comment_body, String comment_photo, String comment_video, String comment_gif, User user, Topic topic) {
        this.comment_body = comment_body;
        this.comment_photo = comment_photo;
        this.comment_video = comment_video;
        this.comment_gif = comment_gif;
        this.user = user;
        this.topic = topic;
    }

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_body() {
        return comment_body;
    }

    public void setComment_body(String comment_body) {
        this.comment_body = comment_body;
    }

    public String getComment_photo() {
        return comment_photo;
    }

    public void setComment_photo(String comment_photo) {
        this.comment_photo = comment_photo;
    }

    public String getComment_video() {
        return comment_video;
    }

    public void setComment_video(String comment_video) {
        this.comment_video = comment_video;
    }

    public String getComment_gif() {
        return comment_gif;
    }

    public void setComment_gif(String comment_gif) {
        this.comment_gif = comment_gif;
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
