package com.javaforum.JavaForum;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.javaforum.JavaForum.models.Comment;
import com.javaforum.JavaForum.models.Role;
import com.javaforum.JavaForum.models.Topic;
import com.javaforum.JavaForum.models.User;
import com.javaforum.JavaForum.models.UserRoles;
import com.javaforum.JavaForum.services.CommentService;
import com.javaforum.JavaForum.services.RoleService;
import com.javaforum.JavaForum.services.TopicService;
import com.javaforum.JavaForum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;



@Transactional
@ConditionalOnProperty(
        prefix = "command.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    TopicService topicService;

    @Autowired
    CommentService commentService;


    @Transactional
    @Override
    public void run(String[] args) throws Exception {
        userService.deleteAll();
        roleService.deleteAll();
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);



        // admin, data, user
        User u1 = new User("admin",
                "password",
                "admin@lambdaschool.local");
        u1.getRoles()
                .add(new UserRoles(u1,
                        r1));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r2));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r3));
        userService.save(u1);

        // data, user
        User u2 = new User("cinnamon",
                "1234567",
                "cinnamon@lambdaschool.local");

        u2.getRoles()
                .add(new UserRoles(u2,
                        r2));
        u2.getRoles()
                .add(new UserRoles(u2,
                        r3));

        userService.save(u2);

        // user
        User u3 = new User("barnbarn",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local");
        u3.getRoles()
                .add(new UserRoles(u3,
                        r2));

        userService.save(u3);

        User u4 = new User("puttat",
                "password",
                "puttat@school.lambda");
        u4.getRoles()
                .add(new UserRoles(u4,
                        r2));
        userService.save(u4);

        User u5 = new User("misskitty",
                "password",
                "misskitty@school.lambda");
        u5.getRoles()
                .add(new UserRoles(u5,
                        r2));
        userService.save(u5);


        User u6 = new User("fart", "password", "email@email.com");
        u6.getRoles()
                .add(new UserRoles(u6,
                        r1));
        u6.getRoles()
                .add(new UserRoles(u6,
                        r2));
        u6.getRoles()
                .add(new UserRoles(u6,
                        r3));

        userService.save(u6);


        Topic t1 = new Topic("fart", "fart","fart","fart", "fart", false, LocalDateTime.now(), u1);
        topicService.save(4,t1);

        Topic t2 = new Topic("farting", "farting","farting","farting", "farting", true, LocalDateTime.now(), u1);
        topicService.save(4,t2);

        Topic t3 = new Topic("test", "test","test","test", "test", false, LocalDateTime.now(), u1);
        topicService.save(4,t3);

        Comment c1 = new Comment("commentBody", "commentPhoto", "commentVideo", "commentGif", LocalDateTime.now(), u1, t1);
        commentService.save(4,10,c1);

        Comment c2 = new Comment("commentBody2", "commentPhoto2", "commentVideo2", "commentGif2", LocalDateTime.now(), u1, t1);
        commentService.save(4,10,c2);

        Comment c3 = new Comment("commentBody", "commentPhoto", "commentVideo", "commentGif", LocalDateTime.now(), u1, t2);
        commentService.save(4,11,c3);

        Comment c4 = new Comment("commentBody", "commentPhoto", "commentVideo", "commentGif", LocalDateTime.now(), u1, t3);
        commentService.save(4,12,c4);




        if (false) {

            FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                    new RandomService());
            Faker nameFaker = new Faker(new Locale("en-US"));

            for (int i = 0; i < 25; i++) {
                new User();
                User fakeUser;

                fakeUser = new User(nameFaker.name()
                        .username(),
                        "password",
                        nameFaker.internet()
                                .emailAddress());
                fakeUser.getRoles()
                        .add(new UserRoles(fakeUser,
                                r2));
                userService.save(fakeUser);
            }
        }
    }
}