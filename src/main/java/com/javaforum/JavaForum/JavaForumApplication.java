package com.javaforum.JavaForum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class JavaForumApplication {

	@Autowired
	private static Environment env;

	private static boolean stop = false;

	private static void checkEnvironmentVariable(String envvar) {
		if (System.getenv(envvar) == null) {
			stop = true;
		}
	}

	public static void main(String[] args) {
		checkEnvironmentVariable("OAUTHCLIENTID");
		checkEnvironmentVariable("OAUTHCLIENTSECRET");

		if (!stop) {
			SpringApplication.run(JavaForumApplication.class, args);
		} else {
			System.out.println("Either or both the environment variables OAUTHCLIENTID, OAUTHCLIENTSECRET are not set. " +
					"They are required for this application to run");
		}
	}
}
