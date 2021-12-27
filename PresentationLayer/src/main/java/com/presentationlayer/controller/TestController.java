package com.presentationlayer.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/test")
    public Map<String, Object> test() {
        return Collections.singletonMap("message", "Test");
    }
}
