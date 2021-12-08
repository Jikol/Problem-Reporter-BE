package com.presentationlayer;

import com.dataaccesslayer.Database;
import com.domainlayer.UserModule;
import com.domainlayer.dto.UserDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class PresentationLayerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PresentationLayerApplication.class, args);

        Database.Create("localhost", "5432", "problem_reporting",
                "postgres", "postgres").Connect();

//        UserModule userModule = new UserModule();
//        List<UserDTO> users = new ArrayList<>();
//        users.add(new UserDTO("email@email.com", "Jan", null));
//        users.add(new UserDTO(null, "Karel", "Bukvice"));
//        users.add(new UserDTO("test@testik.cz", null, null));
//
//        int count = 0;
//        for (UserDTO user : users) {
//            count = userModule.CreateUser(user);
//        }
//        System.out.printf("%s users have been added to database", count);
    }
}
