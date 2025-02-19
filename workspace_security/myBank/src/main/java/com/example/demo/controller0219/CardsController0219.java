package com.example.demo.controller0219;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class CardsController0219 {

	@GetMapping("/getCardsDetail")
	public String getCardsDetail() {
		log.info("getCardsDetail");
		return "DB에서 가져온 카드 세부 정보";
	}
}
