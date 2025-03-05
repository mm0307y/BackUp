package com.example.demo.model;

import lombok.Data;
//회원가입 할 땐
@Data
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private Role role;
}
