package com.presentationlayer.controller;

import com.domainlayer.UserModule;
import com.domainlayer.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @GetMapping("/list-users")
    public List<UserDTO> execute() {
        UserModule userModule = new UserModule();

    }
}
