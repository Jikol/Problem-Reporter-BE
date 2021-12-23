package com.presentationlayer.controller;

import com.domainlayer.UserTM;
import com.domainlayer.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @PostMapping("/user/register")
    public void userRegister(@RequestParam("email") String email,
                             @RequestParam("passwd") String passwd,
                             @RequestParam("name") String name,
                             @RequestParam("surname") String surname) {
        UserTM userTM = new UserTM();
        UserDTO userDTO = new UserDTO(email, passwd, name, surname);
        userTM.RegisterUser(userDTO);
    }
}
