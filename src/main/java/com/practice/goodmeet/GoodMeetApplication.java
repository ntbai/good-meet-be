package com.practice.goodmeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/** Entry point for the Good Meet backend application. */
@SpringBootApplication
@ConfigurationPropertiesScan
public class GoodMeetApplication {

  /**
   * Starts the Spring Boot application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(GoodMeetApplication.class, args);
  }
}
