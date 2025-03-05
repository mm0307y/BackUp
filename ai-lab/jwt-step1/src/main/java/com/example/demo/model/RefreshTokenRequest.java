package com.example.demo.model;

import lombok.Data;
//refreshToken을 새로 받을 때
@Data
public class RefreshTokenRequest {
    private String token;
}
