-- MEMBER250120, REACT_BOARD, REACT_BOARD_COMMENT 
-- 글 목록을 조회시 작성자 정보를 member250120테이블과 Natural Join(equls join)
-- 검색한다.
-- A집합과 B집합 모두에 있는 것 보여줘
-- 회원제 사이트에서는 가입자가 인증 후 이메일로 작성자 처리된다.
-- A집합에 있는데 B집합에 없는 것 가져와 - Outer JOIN
-- Outer JOIN 예) 게시글은 작성했지만 첨부 파일은 없는 경우
-- board테이블에는 insert발생했다. 첨부파일 테이블에는 insert할 필요가 없다.
-- 카타시안곱 (JPA처리 어렵다) : 데이터를 복제하기, Natural, SELF, OUTER

-- 미리 검증하고 select문을 통해서 목록 페이지에 화면 정의서를 작성할 수 있다.
-- SQL문을 미리 작성해 보는 것은 기능을 구현하는데 필요한 조각이다.
-- ERD를 작성한 후에 샘플 데이터를 붓고 테스트 쿼리문을 먼저 돌려본다.
-- ERD가 제대로 그려졌나?


SELECT * FROM react_board
ORDER BY b_no desc;

SELECT * FROM react_board
where b_no=12;

SELECT * FROM react_board_comment;


SELECT * FROM member250120;

delete from member250120 where mem_no=14;

commit;

INSERT INTO react_board(b_no, b_title, email, b_content, b_hit, b_date)
 VALUES(seq_reactboard_no.nextval, #{b_title}, #{email}
      , #{b_content}, 0, to_char(sysdate, 'YYYY-MM-DD')); 
      
INSERT INTO react_board(b_no, b_title, email, b_content, b_hit, b_date)
 VALUES(seq_reactboard_no.nextval, '글제목 연습', 'happy@hot.com'
      , '글내용 연습', 0, to_char(sysdate, 'YYYY-MM-DD'));     
      
SELECT * FROM react_board;      
      
UPDATE react_board
       SET b_title = '글제목 연습21'
          ,email = 'apple@hot.com'
          ,b_content = '글내용 연습21' 
WHERE b_no = 7;      
        



SELECT rb.b_no, rb.b_title, rb.b_content, rb.b_hit, rb.b_date, rm.mem_nickname
FROM react_board rb, member250120 rm
WHERE rb.email = rm.mem_email;

SELECT rb.b_no, rb.b_title, rb.email ,rb.b_content, rb.b_hit, rb.b_date
     , rm.mem_nickname
FROM react_board rb, member250120 rm
WHERE rb.email = rm.mem_email;

SELECT bcom.bc_no, bcom.email, bcom.bc_comment, bcom.bc_date
     ,  bcom.b_no, me.mem_nickname
FROM react_board_comment bcom, member250120 me
WHERE bcom.email =  me.mem_email
  AND bcom.b_no = 1;


commit;

SELECT bcom.bc_no, bcom.email, bcom.bc_comment, bcom.bc_date
     ,  bcom.b_no, me.mem_nickname
FROM react_board_comment bcom, member250120 me
WHERE bcom.email =  me.mem_email
  AND bcom.b_no = #{b_no};

--상세보기가 조회되면 - 1건이 조회되면 - bList.size()==1
--리스트의 크기가 1이면 상세보기가 되었음. - b_hit = b_hit + 1

UPDATE react_board
   SET b_hit = b_hit + 1
 WHERE b_no = #{b_no}; 
 
UPDATE react_board
   SET b_hit = b_hit + 1
 WHERE b_no =:x;
 
DELETE FROM react_board
 WHERE b_no = :x; 
 
rollback;
 
 
commit;   

SELECT * FROM react_board WHERE b_no=7;

--Back-End : SELECT , INSERT, UPDATE, DELETE
-- 공통: REST API이해
--Front-End : 파라미터 넘기기({},[]), 리턴값 받아오기(string, json, []) 

-- 백엔드 : 한건조회(select - 1건 성공) + 한건업데이트(update - b_hit+1)
-- 백엔드측에서는 하나의 업무를 처리하는데 두 개의 DML이 요구되는 경우가 있다.
-- 하나의 업무를 처리하는데 여러개의 insert, update, delete가 멀티로 필요한 경우
-- commit이나 rollback처리요구상황 - 트랜잭션처리
-- 하나의 XXXLogic클래스의 메소드내에서 XXXDao클래스의 여러 메소드를 호출하는 경우가 있다.
-- 트랜잭션처리 대상 후보

update react_board
    set b_title = '바뀐제목'
    , b_content = '바뀐내용'
where b_no = 14;

-- b_no가 없는 값이거나 아니면 어떤 이유로 갑시 전달되지 않았다면

select * from member250120;

select * From react_board;

SELECT rb.b_no, rb.b_title, rb.b_content, rb.b_hit, rb.b_date, mem.mem_name
		FROM react_board rb, member250120 mem
		WHERE rb.email = mem.mem_email;

edit react_board;

select * from REACT_BOARD_COMMENT;
delete from react_board_comment
where b_no = 14;

commit;