package com.presentationlayer.controller;

import com.domainlayer.UserTM;
import com.domainlayer.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @GetMapping("/register-user")
    public void registerUser() {
        UserTM userTM = new UserTM();
        UserDTO userDTO = new UserDTO('petr@seznam.cz', 'Petr', 'Novák');
    }
}
