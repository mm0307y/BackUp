package com.example.demo.controller0218;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

@Log4j2 // 롬복에서 제공하는 고르 출력
@Controller // 페이지 처리와 메서드 이름 앞에 @ResponsBody를 붙여서 평문처리도 가능
public class IndexController0218 {
  @GetMapping({"", "/"})
  public String index() {
    log.info("index");
    // 응답 페이지에 대한 URL패턴 작성 - ViewResolver

    return "index0218"; // resources/templates/index0218.html 호출한다. - DispatcherServlet가 해준다.
  }

  @GetMapping("/user")
  public @ResponseBody String user() {
    log.info("user");
    return "user 페이지 입니다.";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/admin")
  public @ResponseBody String admin() {
    log.info("admin");
    return "admin";
  }
}
