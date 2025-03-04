package com.example.demo.dao0304;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.model0304.User0304;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class UserDao0304 {
  @Autowired
  private SqlSessionTemplate sqlSessionTemplate;

  // 리액트 로그인 화면에서 username과 비번을 입력했을 때 호출되는 메소드 입니다.
  public User0304 findByUsername(String username) {
    User0304 user = sqlSessionTemplate.selectOne("findByUsername", username);
    return user;
  }

  // 회원 가입 구현
  public int userInsert(User0304 user) {
    int result = 0;
    result = sqlSessionTemplate.insert("userInsert", user);
    return result;
  }
}
