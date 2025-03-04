package com.example.demo.model0304;

import lombok.Data;

//로그인 성공 후에 발급 받은 accessToken과 refreshToken을 동시에 담기
//로그인한 시간에서 현재 시간을 차를 구하면 흘러간 시간이 나옴
//시간이 파기시간과 일치하기 60초 전에 토큰을 연장하시겠습니까?
//로그인 성공시 DB에서 꺼내온 정보를 담아서 리액트로 전달하기
//리액트에서는 localStorage에 저장했다가 활용하기
@Data
public class JwtAuthenticationResponse0304 {
  private String accessToken;
  private String refreshToken;
  private Role0304 role;
  private String username;
  private int id;
  private String email;
}
