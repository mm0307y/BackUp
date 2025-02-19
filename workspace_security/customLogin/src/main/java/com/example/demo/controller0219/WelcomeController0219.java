package com.example.demo.controller0219;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
public class WelcomeController0219 {

	@GetMapping({"", "/"})
	public @ResponseBody String welcome() {
		log.info("welcome");
		return "안녕하세요.";
	}

	// 아래 요청은 개발자가 제공하는 화면으로 리다이렉트 되기 위해
	// 추가 되었다.
	// -> http://localhost:9000/user 엔터를 하면 스프링 시큐리티가 인터셉트해서
	// 권한 체크 -> 권한이 없다. -> response.sendRedirect("/loginPage")
	// 리다이렉트를 하면 주소창이 바뀐다. /user -> /loginPage로 바뀌었다.
	// http://localhost:9000/notice 는 authenticated() -> permitAll에 걸린다. ->
	// 인터셉트 당하지 않고(통과하고) -> notice있나? 없다 -> 404번 발생하는게
	// NoticeController가 없기 때문이다.
	@GetMapping("/loginPage")
	public String loginPage() {
		log.info("loginPage");
		return "loginPage0219";
	}
}
