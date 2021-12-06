package com.presentationlayer;

import com.domainlayer.DomainClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PresentationLayerApplication {
    public static void main(String[] args) {
        SpringApplication.run(PresentationLayerApplication.class, args);
        String fromDomain = DomainClass.getName();
        System.out.println(fromDomain);

        String fromData = DomainClass.getNameFromDataAccessLayer();
        System.out.println(fromData);
    }
}
