package com.presentationlayer.controller;

import com.domainlayer.UserTM;
import com.domainlayer.dto.user.LoginUserDTO;
import com.domainlayer.dto.user.NewUserDTO;
import com.domainlayer.dto.user.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<String> userRegister(@RequestBody NewUserDTO user) {
        UserTM userTM = new UserTM();
        Map message = userTM.RegisterUser(user);
        JsonObjectBuilder target = Json.createObjectBuilder()
                .add("timestamp", new Date(System.currentTimeMillis()).toString())
                .add("status", (Integer) message.get("status"));
        if (message.get("error") != null) {
            target.add("errorMessage", message.get("error").toString());
        }
        if (message.get("token") != null) {
            target.add("token", message.get("token").toString());
        }
        return new ResponseEntity(target.build().toString(), HttpStatus.valueOf((Integer) message.get("status")));
    }

    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody LoginUserDTO user) {
        UserTM userTM = new UserTM();
        userTM.loginUser(user);
        return null;
    }
}
