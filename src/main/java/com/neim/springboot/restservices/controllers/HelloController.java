package com.neim.springboot.restservices.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/welcome")
    public String helloWorld() {
        return "Hello Nestor";
    }

    @GetMapping("/user-bean")
    public UserDetails userDetails() {
        return new UserDetails("Nestor Mazariego", "neim1996@hotmail.com", "San Jose");
    }
}
