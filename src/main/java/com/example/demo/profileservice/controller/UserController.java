package com.example.demo.profileservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

  //  @Value("${app.say-hello}")
  //  private String info;

  @GetMapping("/me")
  //  @PreAuthorize("hasAuthority('ROLE_User')")
  @PreAuthorize("hasAuthority('User')")
  public String getMyInfo() {
    return "info";
  }
}
