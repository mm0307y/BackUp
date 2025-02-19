package com.example.demo.controller0217;

import com.example.demo.model0217.User0217;
import com.example.demo.repository0217.UserRepository0217;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// 1. RestController를 사용하면 text로 출력이 나감(text/plain, application/json)
// 2. Controller를 사용하면 요청이 페이지로 출력이 나감
// 단 만일 text나 json포맷을 원하는 경우 리턴타입 앞에 @ResponseBody

@Log4j2
@Controller
public class IndexController0217 {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder = null;

	@Autowired
	private UserRepository0217 userRepository0217;

	@GetMapping({"", "/"})
	public String index() {
		log.info("index");
		// Spring ViewResolver
		return "index0217"; // resources/templates/index0217.html
	}

	@GetMapping("/user")
	public @ResponseBody String user() {
		log.info("user");
		return "user";
	}

	@GetMapping("/manager")
	public @ResponseBody String manager() {
		log.info("manager");
		return "매니저 페이지 입니다.";
	}

	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data() {
		log.info("data");
		return "데이터 정보";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		log.info("admin");
		return "관리자 페이지 입니다.";
	}

	// 스프링 시큐리티에서 낚아채서 아래 코드가 실행이 안된다.
	// 낚아채지 못하는 설정하기
	@GetMapping("/loginForm0217")
	public String loginPage() {
		// 리턴타입 앞에 @ResponseBody가 없으니까 페이지 출력 요청한다.
		return "auth0217/loginForm0217"; // resources/templates/auth0217/loginForm0217.jsp
	}
    /*
    login처리는 시큐리티가 낚아채서 처리해주므로 아래 코드는 필요없다.
    UserDetailsService 클래스로 정의된 loadUserByUsername 메서드를 호출한다.
    사용자 정의 로그인을 진행하려면 loadUserByUsername을 메서드 오버라이딩 한다.
    구현체 클래스를 생성하시오.
      @GetMapping("/loginProcess")
      public @ResponseBody String loginProcess() {
      return "이 코드는 필요없다.";
      }
     */

	// 회원가입 페이지 열기
	@GetMapping("/joinForm")
	public String joinForm() {
		return "auth0217/joinForm0217";
	}

	// 회원가입 처리하기
	@PostMapping("/join")
	public String join(User0217 user) {
		user.setRole("ROLE_ADMIN");
		String rawPassword = user.getPassword();

		// 비밀번호는 스프링 시큐리티가 권장하는 암호화를 거쳐서 내보낸다.
		// 암호화를 처리하지 않으면 로그인 처리가 불가하다.
		// https://bcrypt-generator.com/ 사이트에서 평문에 대한 비밀번호 값을 해시값으로 확인 가능하다.
		String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);

		// rawPassword로 회원가입이 되지만 이럴 경우 로그인 시에 문제가 발생한다.
		// 이유는 패스워드가 암호화가 안되었기 때문에
		user.setPassword(encodedPassword);
		return "redirect:/loginForm0217"; // 회원가입이 성공하면이 요청을 리다이렉트 해준다.
	} // end of 회원가입

	@GetMapping("/custom-login")
	public String loginPage(@RequestParam(value = "error", required = false ) String error, Model model) {
		if (error != null) {
			model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘 못 되었습니다.");
		}
		return "auth0217/loginForm0217"; // 에러 메시지가 나가는 페이지는 auth0217/loginForm.html이다.
	}
}
