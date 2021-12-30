package com.presentationlayer.controller;

import com.domainlayer.UserTM;
import com.domainlayer.dto.user.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        UserTM userTM = new UserTM();
        return userTM.listAllUsers();
    }
}
