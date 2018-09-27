package com.example.calendar;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalendarApplication {

    private final static Logger LOGGER = LogManager.getLogger(CalendarApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CalendarApplication.class, args);
    }
}
