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
    }
}
