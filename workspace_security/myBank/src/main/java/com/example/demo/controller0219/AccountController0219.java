package com.example.demo.controller0219;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class AccountController0219 {

	@GetMapping("/getAccountDetail")
	public String getAccountDetail() {
		log.info("getAccountDetail");
		return "DB에서 가져온 계좌 정보";
	}
}

