package com.example.demo.profileservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

  @GetMapping
  public String sayHi() {
    return "hi";
  }

  @GetMapping("/me")
  public String getMyInfo() {
    return "This is my info";
  }
}
