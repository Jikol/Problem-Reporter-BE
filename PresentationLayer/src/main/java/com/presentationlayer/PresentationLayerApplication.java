package com.presentationlayer;

import com.dataaccesslayer.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PresentationLayerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PresentationLayerApplication.class, args);

        Database db = Database.Create();
        db.Connect();

        System.out.println(db);
    }
}
