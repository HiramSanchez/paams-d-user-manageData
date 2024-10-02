package com.paa.dms.user.manage.data;

import com.paa.dms.user.manage.data.constants.APIConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories("com.paa.*")
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println(APIConstants.SERVICE_START);
    }
}