package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig0219 {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/user/**").authenticated() // 인증만 되면(권한에 상관없이) 들어갈 수 있는 주소
						.requestMatchers("/admin/**").hasAnyRole("ADMIN") // 인증만 되면(권한에 상관없이) 들어갈 수 있는 주소
						.requestMatchers("*").permitAll()) // 위에 두가지를 제외하고는 모두 허락한다.
				.formLogin(form -> form
						// 페이지 이름을 직접 호출하는 것은 불가하다. - 그냥 화면뿐인 페이지인데 컨트롤을 꼭 태워야 하나?
						// Spring을 경유하지 않고 처리되는 것에 대해서 불가하다. - DispatcherServlet를 경유한다.
						.loginPage("/loginPage").permitAll()) // 개발자가 제공하는 로그인 화면으로 연결된다.
				.httpBasic(Customizer.withDefaults()); // 스프링이 지원하는 인증 프로세스 탄다.
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		// 원칙적으로는 평문의 비밀번호는 처리가 안되지만 비밀번호 앞에 {noop} 예약어를 붙여 주면 통과 된다.
		// 비밀번호를 해시값으로 변경해서 인코딩을 해야 통과 되는건데 해준다.
		UserDetails user = User.withUsername("user").password("{noop}12345").authorities("read").build();
		UserDetails admin = User.withUsername("admin").password("{noop}12345").authorities("admin").build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	/*
	spring boot 버전에 따라 {noop}에러가 발생하거나 인증이 실패한다면 아래 코드를 활용할 것
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	 */
}
