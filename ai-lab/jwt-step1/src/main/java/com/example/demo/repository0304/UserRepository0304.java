package com.example.demo.repository0304;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model0304.User0304;

//CRUD함수를 JpaRepository가 제공하고 있다.
//@Repository 어노테이션을 없어도 IoC가 자동으로 됨. 왜냐면 JapRepository를 상속 받았으니까
public interface UserRepository0304 extends JpaRepository<User0304, Integer> {
  // 메소드 접두어 findBy까지는 규칙이고 Username은 문법이다.
  // select * from user where username=? 자동으로 생성 해줌.
  // 메소드 이름을 가지고 쿼리문을 작성해 준다. - JPA
  public User0304 findByUsername(String username);
  // 만일 select * from user whre email = ?
  // 위 쿼리문을 생성하고 싶다면 메소드 이름은 어떻게 작명해야 하나요?
  // public User findByEmail(String email); jpa query method 검색해 보세요.
}
