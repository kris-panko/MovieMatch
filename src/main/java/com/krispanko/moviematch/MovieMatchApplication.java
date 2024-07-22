package com.krispanko.moviematch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.krispanko.moviematch.repository")
@EntityScan(basePackages = "com.krispanko.moviematch.model")
public class MovieMatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieMatchApplication.class, args);
    }
}
