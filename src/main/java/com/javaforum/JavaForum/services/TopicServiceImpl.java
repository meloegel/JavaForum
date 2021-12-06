package com.javaforum.JavaForum.services;

import com.javaforum.JavaForum.exceptions.ResourceNotFoundException;
import com.javaforum.JavaForum.models.Topic;
import com.javaforum.JavaForum.models.User;
import com.javaforum.JavaForum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Service(value = "topicService")
public class TopicServiceImpl implements TopicService{
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;


    @Override
    public List<Topic> findAll() {
        List<Topic> list = new ArrayList<>();
        topicRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Topic findByName(String topicname) {
        Topic topic = topicRepository.findByTopicname(topicname.toLowerCase().replaceAll("_", " "));
        if (topic == null) {
            throw new  ResourceNotFoundException("Topic with name " + topicname + " not found!");
        }
        return topic;
    }

    @Override
    public Topic findById(long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + id + " not found!"));
    }

    @Override
    public List<Topic> findByNameContaining(String topicname) {
        return topicRepository
                .findByTopicnameContainingIgnoreCase(topicname.toLowerCase().replaceAll("_", " "));
    }

    @Transactional
    @Override
    public void delete(long id) {
        topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + id + " not found!"));
        topicRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Topic save(long userid, Topic topic) {
        Topic newTopic = new Topic();
        if (topic.getTopicid() != 0) {
            topicRepository.findById(topic.getTopicid())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Topic id " + topic.getTopicid() + " not found!"));
            newTopic.setTopicid(topic.getTopicid());
        }
        newTopic.setTopicname(topic.getTopicname());
        newTopic.setTopicbody(topic.getTopicbody());
        newTopic.setTopicphoto(topic.getTopicphoto());
        newTopic.setTopicvideo(topic.getTopicvideo());
        newTopic.setTopiclink(topic.getTopiclink());
        newTopic.setNsfw(topic.getNsfw());
        newTopic.setTimepostedtopic(LocalDateTime.now());

        User userInfo = userService.findUserById(userid);
        newTopic.setUser(userInfo);

        return topicRepository.save(newTopic);
    }

    @Transactional
    @Override
    public Topic update(Topic topic, long id) {
        Topic newTopic = new Topic();
        if (topic.getTopicname() == null) {
            throw new ResourceNotFoundException("Topic with id " + id + " not found!");
        }
        if (topic.getTopicname() != null) {
            newTopic.setTopicname(topic.getTopicname());
        }
        if (topic.getTopicbody() != null) {
            newTopic.setTopicbody(topic.getTopicbody());
        }
        if (topic.getTopicphoto() != null) {
            newTopic.setTopicphoto(topic.getTopicphoto());
        }
        if (topic.getTopicvideo() != null) {
            newTopic.setTopicvideo(topic.getTopicvideo());
        }
        if (topic.getTopiclink() != null) {
            newTopic.setTopiclink(topic.getTopiclink());
        }
        if (topic.getNsfw() != null) {
            newTopic.setNsfw(topic.getNsfw());
        }
        newTopic.setTimepostedtopic(LocalDateTime.now());

        return topicRepository.save(newTopic);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll() {
        topicRepository.deleteAll();
    }
}
