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
    private long topicid;

    @Column(nullable = false, unique = true)
    private String topicname;

    @Column
    private String topicbody;

    @Column
    private String topicphoto;

    @Column
    private String topicvideo;

    @Column
    private String topiclink;

    @Column
    private Boolean nsfw;

    @Column(nullable = false)
    private LocalDateTime timeposted;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = {"topics", "roles"}, allowSetters = true)
    private User user;

    @OneToMany(mappedBy = "comment_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "topic_id", allowSetters = true)
    private List<Comment> comments = new ArrayList<>();

    public Topic() {}

    public Topic(String topicname, LocalDateTime timeposted, User user) {
        this.topicname = topicname;
        this.timeposted = timeposted;
        this.user = user;
    }

    public Topic(String topicname, String topicbody, String topicphoto, String topicvideo, String topiclink, Boolean nsfw, LocalDateTime timeposted, User user) {
        this.topicname = topicname;
        this.topicbody = topicbody;
        this.topicphoto = topicphoto;
        this.topicvideo = topicvideo;
        this.topiclink = topiclink;
        this.nsfw = nsfw;
        this.timeposted = timeposted;
        this.user = user;
    }

    public Topic(String topicname, String topicbody, String topicphoto, String topicvideo, String topiclink, Boolean nsfw, LocalDateTime timeposted, User user, List<Comment> comments) {
        this.topicname = topicname;
        this.topicbody = topicbody;
        this.topicphoto = topicphoto;
        this.topicvideo = topicvideo;
        this.topiclink = topiclink;
        this.nsfw = nsfw;
        this.timeposted = timeposted;
        this.user = user;
        this.comments = comments;
    }

    public long getTopicid() {
        return topicid;
    }

    public void setTopicid(long topicid) {
        this.topicid = topicid;
    }

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public String getTopicbody() {
        return topicbody;
    }

    public void setTopicbody(String topicbody) {
        this.topicbody = topicbody;
    }

    public String getTopicphoto() {
        return topicphoto;
    }

    public void setTopicphoto(String topicphoto) {
        this.topicphoto = topicphoto;
    }

    public String getTopicvideo() {
        return topicvideo;
    }

    public void setTopicvideo(String topicvideo) {
        this.topicvideo = topicvideo;
    }

    public String getTopiclink() {
        return topiclink;
    }

    public void setTopiclink(String topiclink) {
        this.topiclink = topiclink;
    }

    public Boolean getNsfw() {
        return nsfw;
    }

    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    public LocalDateTime getTimeposted() {
        return timeposted;
    }

    public void setTimeposted(LocalDateTime timeposted) {
        this.timeposted = timeposted;
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
