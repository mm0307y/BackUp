package com.example.demo.controller0219;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class NoticeController0219 {

	@GetMapping("/notices")
	public String getNotices() {
		log.info("getNotices");
		return "DB에서 가져온 공지사항";
	}
}
