package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.model.SignupRequest;
import com.example.demo.model.User;

import org.mybatis.spring.SqlSessionTemplate;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class UserDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    //리액트 로그인 화면에서 username과 비번을 입력했을 때 호출되는 메소드 입니다.
    public User findByUsername(String username){
        User user = sqlSessionTemplate.selectOne("findByUsername", username);
        return user;
    }
    //회원 가입 구현
    public int signup(SignupRequest signupRequest){
        int result = 0;
        result = sqlSessionTemplate.insert("userInsert", signupRequest);
        return result;
    }
}
