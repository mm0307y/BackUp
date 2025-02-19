package com.example.demo.config0218;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // 시큐어 메서드 어노테이션 활성화
public class SecurityConfig0218 {
  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests((requests) -> requests
    // authenticated와 permitAll 조합으로 설정되어다면 이것은 연습 용이다.
        .requestMatchers("/user/**").authenticated()
        .requestMatchers("*").permitAll())
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    // Approach1 passwordEncoder 없이 테스트 (deprecated 뜸)

    UserDetails admin = User.withUsername("admin")
        .password("12345") // 비밀번호가 평문이다.
        .roles("ADMIN") // ROLE_ADMIN
        .build();
    UserDetails manager = User.withUsername("manager")
        .password("12345")
        .roles("MANAGER") // ROLE_MANAGER
        .build();
    UserDetails user = User.withUsername("user")
        .password("12345")
        .roles("USER") // ROLE_USER
        .build();

    return new InMemoryUserDetailsManager(admin, manager, user);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}
