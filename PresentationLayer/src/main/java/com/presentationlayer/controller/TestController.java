package com.presentationlayer.controller;

import com.domainlayer.UserTM;
import com.domainlayer.dto.user.UserDTO;
import com.presentationlayer.module.JsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/users")
    public Object getUsers(@RequestHeader("Authorization") String token) {
        UserTM userTM = new UserTM();
        var response = userTM.listAllUsers(token);
        if (response instanceof Map) {
            return new ResponseEntity<String>(JsonBuilder.BuildResponseJson((Map) response),
                    HttpStatus.valueOf((Integer) ((Map) response).get("status")));
        }
        if (response instanceof List) {
            return response;
        }
        return null;
    }
}
