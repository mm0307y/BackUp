package com.example.demo.model0217;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

// JPA에서는 Hibernate를 활용한 DB연동 지원
// 테이블 생성도 클래스 설계된 내용으로 create table이 만들어진다.
// 만일 두 번째 테스트부터는 기존에 만들어진 테이블을 사용하고 싶다면
// yamul 파일에서 create -> update 바꾸어 준다.
// 기존에 테이블이 모두 삭제되고 매번 테스트 시 마다 테이블이 새로 생성된다. - 주의

@Entity
@Data
public class User0217 {
  @Id // primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String username;
  private String password;
  private String email;
  private String role; // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
  @CreationTimestamp
  private Timestamp createDate;
}
