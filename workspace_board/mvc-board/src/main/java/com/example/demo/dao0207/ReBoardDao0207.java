package com.example.demo.dao0207;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.model0206.ReactBoard0206;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class ReBoardDao0207 {
  // ReBoardDao가 자바 클래스 끝단
  // SqlSessionFactory -> SqlSessionFactoryBean
  // SqlSession -> SqlSessionTemplate
  // implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3' -> jar
  // myBatis layer를 통해서 DB연동을 한다. - 30%이상 줄어든다. - JPA(Hibernate - SQL문이 없다.)
  // application.yml을 참조해서 SqlSessionFactoryBean - 오라클 서버 연결 통로 추가 - 커넥션 생성
  // 이 인스턴스를(생성자의 파라미터로 전달 받아서) 가지고 SqlSessionTemplate생성된다.
  // 백엔드 개발자 - 공통팀

  @Autowired (required = false) // 왜 Autowired가 또 필요한가?
  private SqlSessionTemplate sessionTemplate = null;
  public List<Map<String, Object>> boardList(Map<String, Object> pmap) {
    log.info("boardList 호출");

    // gubun은 검색조건의 컬럼명 - b_title, b_content, mem_nickname
    log.info(pmap.get("gubun") + ", " + pmap.get("keyword"));
    List<Map<String, Object>> bList = null;
    bList = sessionTemplate.selectList("boardList", pmap);
    log.info("bList : " + bList);
    return bList;
  }

  public int boardInsert(ReactBoard0206 board) {
    log.info("boardInsert 호출");
    int result = -1;
    result = sessionTemplate.insert("boardInsert", board);
    return result;
  }

  public void hitCount(Map<String, Object> pmap) {
    log.info("hitCount호출 성공");
    sessionTemplate.update("hitCount", pmap);
  } // end of hitCount

  // 원글에 대한 댓글 조회하기
  public List<Map<String, Object>> commentList(Map<String, Object> pmap) {
    log.info("commentList 호출 성공");
    List<Map<String, Object>> commList = null;
    commList = sessionTemplate.selectList("commentList", pmap);
    log.info(commList);
    return commList;
  }

  public int boardDelete(int b_no) {
    log.info("boardDelete 삭제 성공");
    int result = -1;
    // delete from react_board where b_no=#{value}
    result = sessionTemplate.delete("boardDelete", b_no);
    return result;
  } // end of boardDelete

  public int boardUpdate(Map<String, Object> pmap) {
    log.info("boardUpdate 수정 성공");
    int result = -1;
    result = sessionTemplate.update("boardUpdate", pmap);
    log.info(result); // 1이면 수정 성공, 0이면 수정 실패
    return result;
  } // end of boardUpdate

  public int commentInsert(Map<String, Object> pmap) {
    log.info("commentInsert 댓글 등록 성공");
    int result = -1;
    result = sessionTemplate.insert("commentInsert", pmap);
    return result;
  } // end of commentInsert

  public int commentUpdate(Map<String, Object> pmap) {
    log.info("commentUpdate 댓글 수정 성공");
    int result = -1;
    result = sessionTemplate.update("commentUpdate", pmap);
    return result;
  } // end of commentUpdate

  public int commentDelete(int bc_no) {
    log.info("commentDelete 댓글 삭제 성공");
    int result = -1;
    result = sessionTemplate.delete("commentDelete", bc_no);
    return result;
  } // end of commentDelete
}