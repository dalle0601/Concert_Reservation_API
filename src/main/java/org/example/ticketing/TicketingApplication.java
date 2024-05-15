package org.example.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class TicketingApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketingApplication.class, args);
    }

}
