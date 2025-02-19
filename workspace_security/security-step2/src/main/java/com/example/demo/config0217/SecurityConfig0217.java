package com.example.demo.config0217;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 개발자가 구현한 로그인 화면을 띄우려면 반드시 SecurityConfig 관련 설정을 할 것.
// permitAll() : 특정 경로에 대해 모든 사용자(인증의 여부와 상관 없이)에게 접근을 허락한다.
// @Configuration과 @Bean은 한쌍이다.
// 스프링 컨테이너(BeanFactory, ApplicationContext)로 부터 빈을 관리 받기 위해 사용되는 어노테이션이다.

@Configuration
@EnableMethodSecurity // - URL 매핑된 메서드 권한 부여 활용할 때 사용한다.
public class SecurityConfig0217 {
  // @Bean 해당 메서드의 리턴 되는 오브젝트를 IoC로 등록해준다.
  // 아래 코드를 추가해야 IndexController에서 Autowired로 주입 받을 수 있다.
  // 안하면 컴파일 에러 발생한다.
  @Bean
  public BCryptPasswordEncoder encodePwd() {
    return new BCryptPasswordEncoder();
  }

  // 디폴트로 login 요청을 시큐리티가 낚아채서 미리 약속된 필터를 통과하도록 강제 한다.
  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/user/**").authenticated() // user, manager, admin 권한은 따지지 않고 인증만 되면 보여줘
            .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") // ROLE_MANAGER, ROLE_ADMIN
            .requestMatchers("/admin/**").hasAnyRole("ADMIN")
            .requestMatchers("*").permitAll()) // localhost:9000/notices, or /contact
        .formLogin(form -> form
                .loginPage("/loginForm0217") // 사용자가 정의한 로그인 페이지
//            .usernameParameter("바꿀 이름 작성할 것")
                // login이 호출되면 시큐리티 낚아채서 대신 로그인을 진행한다. (Filter)
                .loginProcessingUrl("/login") // 로그인 요청 처리하는 URL(default: "/login")
                .defaultSuccessUrl("/", true) // 로그인 성공한 후 이동할 페이지(default: login)
                .failureUrl("/custom-login?error=true") // 로그인 실패시 이동할 페이지 설정
                .permitAll()
        ) // 사용자 정의 로그인 페이지 설정 추가가능
        .httpBasic(Customizer.withDefaults());
    return http.build();
  } // end of defaultSecurityFilterChain
} // end of SecurityConfig
