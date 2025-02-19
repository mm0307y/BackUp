package com.example.demo.controller0219;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class LoansController0219 {

	@GetMapping("/getLoansDetail")
	public String getLoansDetail() {
		log.info("getLoansDetail");
		return "DB에서 가져온 대출 세부 정보";
	}
}
