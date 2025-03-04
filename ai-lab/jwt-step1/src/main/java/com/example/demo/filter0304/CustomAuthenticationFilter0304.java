package com.example.demo.filter0304;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilter0304 extends UsernamePasswordAuthenticationFilter {

  // AuthenticationManager를 생성자에서 주입받습니다.
  public CustomAuthenticationFilter0304(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
    // 기본 로그인 URL("/login") 대신 커스텀 URL("/custom-login")로 설정 (필요에 따라 변경)
    setFilterProcessesUrl("/custom-login");
  }

  // 인증 시 호출되는 메서드를 오버라이드합니다.
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {

    // 요청에서 username과 password를 추출합니다.
    String username = obtainUsername(request);
    String password = obtainPassword(request);

    // 만약 null이면 빈 문자열로 초기화 (예외 방지)
    if (username == null) {
      username = "";
    }
    if (password == null) {
      password = "";
    }
    username = username.trim();

    // UsernamePasswordAuthenticationToken 객체 생성
    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

    // 추가 정보를 설정 (예: 클라이언트 IP, 세션 정보 등)
    setDetails(request, authRequest);

    // AuthenticationManager를 통해 인증을 진행합니다.
    return this.getAuthenticationManager().authenticate(authRequest);
  }

  // 필요에 따라 성공/실패 처리 메서드를 오버라이드할 수 있습니다.
  // 예) successfulAuthentication(), unsuccessfulAuthentication() 등
}
