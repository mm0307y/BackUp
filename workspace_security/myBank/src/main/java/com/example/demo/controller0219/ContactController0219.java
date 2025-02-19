package com.example.demo.controller0219;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class ContactController0219 {

	@GetMapping("/contact")
	public String getContact() {
		log.info("getContact");
		return "DB에서 가져온 연락 정보";
	}
}
