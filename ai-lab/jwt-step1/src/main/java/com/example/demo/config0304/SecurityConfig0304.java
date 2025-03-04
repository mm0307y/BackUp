package com.example.demo.config0304;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.model0304.Role0304;
import com.example.demo.service0304.UserService0304;

import lombok.RequiredArgsConstructor;

// 개발자가 구현한 로그인 화면을 띄우려면 반드시 SecurityConfig관련 설정을 해야 한다.
// permitAll(): 특정 경로에 대해 모든 사용자(인증 여부와 상관없이...)에게 접근을 허락한다.
// @Configuration 과 @Bean은 한쌍이다.
// 스프링 컨테이너로 부터 빈을 관리 받기 위해 사용되는 어노테이션이다.
@Configuration
@EnableWebSecurity // 시큐어 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig0304 {
  private final CorsFilter corsFilter; // 반드시 RequiredArgsConstructor 추가할 것. - 컴파일에러 발생하지 않는다.
  private final UserService0304 userService;// UserDetailsService 객체를 제공받기 위해 선언
  // 디폴트로 login요청을 시큐리티가 낚아채서 미리 약속된 필터를 통과하도록 강제 한다.

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(Customizer.withDefaults())
        .addFilter(corsFilter)
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/user/**").hasAnyAuthority(Role0304.USER.name())
            .requestMatchers("/notice/**").hasAnyAuthority(Role0304.ADMIN.name(), Role0304.USER.name())
            .requestMatchers("/admin/**").hasAnyAuthority(Role0304.ADMIN.name())
            .anyRequest().authenticated())
        .formLogin(form -> form
            .loginPage("/loginForm") // 사용자가 정의한 로그인 페이지
            // .usernameParameter("바꿀 이름 작성한다.")
            // login이 호출되면 시큐리티 낚아채서 대신 로그인을 진행한다.(Filter)
            .loginProcessingUrl("/login") // 로그인 요청 처리하는 URL(default: "/login")
            .defaultSuccessUrl("/", true) // 로그인 성공한 후 이동할 페이지(default : login)
            .failureUrl("/custom-login?error=true") // 로그인 실패시 이동할 페이지 설정
            .permitAll()) // 사용자 정의 로그인 페이지 설정 추가가능
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }// end of securityFilterChain

  // 아래 코드가 없으면 user 12345로 로그인 안된다다.
  // spring security 5이상에서는 비밀번호를 저장할 때 반드시 인코딩 방식이 명시되어야 한다.
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    // ArrayList al = new ArrayList(), List al = new ArrayList() al= new Vector()
    // 메소드 설계시에는 리턴 타입 자리에 상위 클래스를 작성하는 것이 좋다. - 유지보수나 재사용성에서 유리하다.
    // AuthenticationProvider대신에 DaoAuthenticationProvider를 사용하여 DB와 연동(로그인)하고
    // 로그인 후에 id, email, username, role 정보를 가져와서 UserDetails타입에 담아야 만
    // 시큐리티 세션 영역에 담을 수 있다. 아무 클래스나 담을 수가 없다.
    // User클래스를 정의할 때 UserDetails implements를 했다.
    // 로그인 화면은 리액트에서 제공하고 있고 사용자가 username과 password입력한다.
    // 로그인 버튼을 누르면 SecurityConfig설정에서 loginProcessingUrl("/login") 이 부분을 말한다.
    // loginProcess 요청이 오면 자동으로 UserDetailsService타입으로 IoC되어 있는
    // loadUserByUsername함수가 실행됨.
    // 이것이 시큐리티 컨벤션이다.
    // UserDetailsService가 선언하고 있는 loadUserByUsername메소드를 재정의 (Overriding) 해야 한다.
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userService.userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }// end of authenticationProvider

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
} // end of SecurityConfig
