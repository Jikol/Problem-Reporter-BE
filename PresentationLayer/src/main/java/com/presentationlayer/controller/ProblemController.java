package com.presentationlayer.controller;

import com.domainlayer.ProblemTM;
import com.domainlayer.dto.problem.NewProblemDTO;
import com.domainlayer.dto.user.RegisterUserDTO;
import com.presentationlayer.module.JsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    @PostMapping("/new/unregister")
    public ResponseEntity<String> reportProblemUnregister(@RequestBody Map requestProblem) {
        var problem = (Map) requestProblem.get("problem");
        var creds = (Map) requestProblem.get("creds");
        NewProblemDTO newProblemDTO = new NewProblemDTO(
                (String) problem.get("title"),
                (String) problem.get("summary"),
                (String) problem.get("configuration"),
                (String) problem.get("expectedBehavior"),
                (String) problem.get("actualBehavior"),
                (String) requestProblem.get("domain"),
                (List) requestProblem.get("attachments"),
                new RegisterUserDTO((String) creds.get("email"), (String) creds.get("passwd"),
                        (String) creds.get("name"), (String) creds.get("surname")
                )
        );
        Map callback = new ProblemTM().CreateNewProblem(newProblemDTO);
        return new ResponseEntity(JsonBuilder.BuildResponseJson(callback),
                HttpStatus.valueOf((Integer) callback.get("status"))
        );
    }

    @PostMapping("/new")
    public ResponseEntity<String> reportProblem(@RequestBody Map requestProblem) {
        return null;
    }
}
