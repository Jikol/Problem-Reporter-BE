package com.presentationlayer.controller;

import com.domainlayer.UserTM;
import com.domainlayer.dto.user.LoginUserDTO;
import com.domainlayer.dto.user.RegisterUserDTO;
import com.presentationlayer.module.JsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody RegisterUserDTO user) {
        UserTM userTM = new UserTM();
        Map message = userTM.RegisterUser(user);
        return new ResponseEntity(JsonBuilder.BuildResponseJson(message),
                HttpStatus.valueOf((Integer) message.get("status")));
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody LoginUserDTO user) {
        UserTM userTM = new UserTM();
        Map message = userTM.loginUser(user);
        return new ResponseEntity(JsonBuilder.BuildResponseJson(message),
                HttpStatus.valueOf((Integer) message.get("status")));
    }
}
