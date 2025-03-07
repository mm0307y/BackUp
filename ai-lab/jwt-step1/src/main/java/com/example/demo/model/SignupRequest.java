package com.example.demo.model;
//이 클래스는 회원가입 전용으로 사용되는 모델 클래스 입니다.
import lombok.Data;
//회원가입 할 땐
@Data
public class SignupRequest {
    private String username;
    private String password;//12345가 아니라 암호화된 비번을 담을 것.-  주의
    private String email;
    private Role role;
}
