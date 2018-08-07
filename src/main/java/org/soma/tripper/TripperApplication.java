package org.soma.tripper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TripperApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripperApplication.class, args);
    }
}
