package com.example.demo.controller0304;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class WelcomeController0304 {
  @GetMapping("/welcome")
  public String welcome() {
    log.info("welcome 호출 성공");
    return "welcome";
  }// end of welcome
}
