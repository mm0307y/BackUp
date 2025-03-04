package com.example.demo.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

//스프링 시큐리티에서는 일반 클래스는 담을 수가 없다.
//반드시 UserDetails타입만 담을 수 있다.

@Data
public class User implements UserDetails {
    private int id;
    private String username;
    private String password;
    private String email;
    private Role role; // USER, ADMIN
    private String createDate;
    //사용자가 가진 권한 정보를 반환하는 메소드 선언
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    //계정 상태 관련 메소드
    //계정이 만료되지 않았음을 의미
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    //계정이 잠겨 있지 않았음을 의미
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }    
    //비밀번호 등의 자격증명이 만료되지 않았음을 의미
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }  
    //계정이 활성화된 상태인지
    @Override
    public boolean isEnabled(){
        return true;
    }  
}
