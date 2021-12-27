package com.presentationlayer.controller;

import com.domainlayer.UserTM;
import com.domainlayer.dto.user.NewUserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.HashMap;
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
        return new ResponseEntity(target.build().toString(), HttpStatus.valueOf((Integer) message.get("status")));
    }
}
