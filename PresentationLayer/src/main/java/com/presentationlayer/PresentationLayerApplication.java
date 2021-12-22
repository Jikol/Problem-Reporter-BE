package com.presentationlayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PresentationLayerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PresentationLayerApplication.class, args);

//        Database.Create("localhost", "5432", "problem_reporting",
//                "postgres", "postgres").Connect();
    }
}
