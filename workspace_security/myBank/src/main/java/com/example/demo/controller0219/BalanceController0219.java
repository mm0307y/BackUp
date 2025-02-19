package com.example.demo.controller0219;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class BalanceController0219 {

	@GetMapping("/getBalanceDetail")
	public String getBalanceDetail() {
		log.info("getBalanceDetail");
		return "DB에서 가져온 잔액 세부 정보";
	}

}
