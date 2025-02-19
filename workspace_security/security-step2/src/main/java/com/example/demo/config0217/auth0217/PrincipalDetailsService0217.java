package com.example.demo.config0217.auth0217;

import com.example.demo.model0217.User0217;
import com.example.demo.repository0217.UserRepository0217;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 이 클래스는 도대체 언제 발동하나요?
// 시큐리티 설정에서 loginProcessingUrl("/login");
// login 요청이 오면 자동으로 UserDetailsService타입(클래스)으로
// IoC(Inversion of Control)되어 있는 loadUserByUsername 메서드가 실행된다.

@Log4j2
@Service
public class PrincipalDetailsService0217 implements UserDetailsService {
	@Autowired
	private UserRepository0217 userRepository0217;

	// 만일 loadUserByUsername이 개발자를 호출하는 메서드가 아니다.
	// 파라미터 변수 이름을 마음대로 바꿀 수 없다. - mem_id, mem_pw
	// mem_email
	// 만일 이름을 바꾸고 싶다면 SecurityConfig에서 usernameParameter("mem_name")로 해주면
	// 파라미터의 이름을 mem_name으로 사용이 가능하다.
	// 이와 같이 해야 하는 이유는 시큐리티가 제공하는 필터(내부 흐름)에 이 부분에 대해 변수 처리가 되어 있기 때문이다.
	// public UserDetails loadUserByUsername(String mem_name) throws UsernameNotFoundException{
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		log.info("사용자가 입력한 유저네임 : " + username);

		// 파라미터로 받은 username이 mysql이 테이블에 있는지 확인 한다.
		User0217 userEntity = userRepository0217.findByUsername(username);
		if(userEntity != null){ // 당신이 입력한 이름은 이미 존재 한다.
			return new PrincipalDetails0217(userEntity);
		}
		return null;
	}
}
