package com.example.demo.controller0304;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service0304.AuthenticationService0304;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController0304 {
  @Autowired
  private AuthenticationService0304 authenticationService;
  // 로그인 처리 - accessToken발급

  // refreshToken발급
}
