package com.example.demo.repository0217;

// 주의 : 스프링 시큐리티가 제공하는 User 클래스를 선택하며 안된다.
import com.example.demo.model0217.User0217;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CRUD 함수를 JpaRepository가 제공하고 있다.
// @Repository // 어노테이션을 없어도 IoC가 자동으로 된다. 왜냐면 JapRepository를 상속 받았으니까
public interface UserRepository0217 extends JpaRepository<User0217, Integer> {
	// 메서드 접두어 findBy까지는 규칙이고 Username은 문법이다.
	// select * from user where username=? 자동으로 생성해 준다.
	// 메서드 이름을 가지고 쿼리문을 작성해 준다. - JPA

	public User0217 findByUsername(String username);
	// 만일 select * from where email = ?
	// 위 쿼리문을 생성하고 싶다면 메서드 이름은 어떻게 작명해야 하나요?
	// public User0217 findByEmail(String email); jpa query method로 검색해보자


}
