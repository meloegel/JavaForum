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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "topics")
public class Topic extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long topic_id;

    @Column(nullable = false, unique = true)
    private String topic_name;

    @Column
    private String topic_body;

    @Column
    private String topic_photo;

    @Column
    private String topic_video;

    @Column
    private String topic_link;

    @Column
    private Boolean nsfw;

    @Column(nullable = false)
    private LocalDateTime time_posted;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = {"topics", "roles"}, allowSetters = true)
    private User user;

    @OneToMany(mappedBy = "comment_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "topic_id", allowSetters = true)
    private List<Comment> comments = new ArrayList<>();

    public Topic() {}

    public Topic(String topic_name, LocalDateTime time_posted, User user) {
        this.topic_name = topic_name;
        this.time_posted = time_posted;
        this.user = user;
    }

    public Topic(String topic_name, String topic_body, String topic_photo, String topic_video, String topic_link, Boolean nsfw, LocalDateTime time_posted, User user, List<Comment> comments) {
        this.topic_name = topic_name;
        this.topic_body = topic_body;
        this.topic_photo = topic_photo;
        this.topic_video = topic_video;
        this.topic_link = topic_link;
        this.nsfw = nsfw;
        this.time_posted = time_posted;
        this.user = user;
        this.comments = comments;
    }

    public long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(long topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getTopic_body() {
        return topic_body;
    }

    public void setTopic_body(String topic_body) {
        this.topic_body = topic_body;
    }

    public String getTopic_photo() {
        return topic_photo;
    }

    public void setTopic_photo(String topic_photo) {
        this.topic_photo = topic_photo;
    }

    public String getTopic_video() {
        return topic_video;
    }

    public void setTopic_video(String topic_video) {
        this.topic_video = topic_video;
    }

    public String getTopic_link() {
        return topic_link;
    }

    public void setTopic_link(String topic_link) {
        this.topic_link = topic_link;
    }

    public Boolean getNsfw() {
        return nsfw;
    }

    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    public LocalDateTime getTime_posted() {
        return time_posted;
    }

    public void setTime_posted(LocalDateTime time_posted) {
        this.time_posted = time_posted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
