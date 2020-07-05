package com.pbednarz.nonameproject.controllerRest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${com.pbednarz.apiPath}")
public class UserControllerRest {

    @GetMapping
    public String home() {
        return "Hello";
    }
}
