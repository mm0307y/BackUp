package com.example.demo.controller0205;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.logic0207.ReBoardLogic0207;
import com.example.demo.model0206.ReactBoard0206;
import com.google.gson.Gson;

import lombok.extern.log4j.Log4j2;

// 선택하기
// 1) @Controller인가, 2) @RestController인가,
// @Controller : 화면을 지정할 수 있다. 와 @RestController(JSON혁향식) 차이점 -> 구글 검색
// 페이지로 출력과를 내보단다. -1번, 만일 나는 React페이지에 출력을 할거야 2번
// 반복되는 코드가 나오면 경계해야 한다. - 줄일 수 없나? - 문제의식 - 코드 변경 긍정적
// React와 Spring Boot 연동하기 (3000 -> 8000 호출하기)
// React서버 설정 - src/setProxy.js -> 접두어 api
// Spring Boot설정

@Log4j2
@RestController
@RequestMapping("/api/*")
public class ReBoardController0205 {
  @Autowired
  private ReBoardLogic0207 reBoardLogic = null; // 선언만 한다. 그러면 ApplicationContext가 관리해준다.
  // 필요할 때 주입해준다.()

  /// //////////////////////////// Quill Editor을 사용하여 이미지 처리하기 구현 ///////////////////////////////
  // Quill Editor에서 이미지를 선택하면 <input type = 'file' name='image'
  // 누가 webapp/pds0207 아래의 폴더에 파일이 생성됨.
  @PostMapping("board/imageUpload")
  public String imageUpload(@RequestParam(value = "image") MultipartFile image) {
    log.info("image : " + image);
    String filename = reBoardLogic.imageUpload(image);
    return filename;
  }

  @GetMapping("board/imageGet")
  public String imageGet(HttpServletRequest req, HttpServletResponse res) {
    // http://localhost:8000/api/board/imageGet?imageName=20250213114202-tiger2.jpg
    String b_file = req.getParameter("imageName");
    log.info("imageGet 호출 성공===>" + b_file);

    // 아래 경로는 서버릐 물리적인 위치 값
    String filePath = "D:\\Java\\workspace_board\\mvc-board\\src\\main\\webapp\\pds0207"; // 절대경로.
    //String filePath ="upload"; // 절대경로.
    String fname = b_file;
    log.info("b_file: 8->euc" + b_file);

    // File클래스는 파일이름만을 객체로 만들어 준다. 내용이 포함되지 않는다.
    // 텍스트파일과 바이너리 파일
    File file = new File(filePath, b_file.trim()); // trim() 공백을 제거하는 함수이다.

    // 마임타입을 읽어오는 코드이다.
    // mimetype - 앞쪽에는 메인타입을 적는다.(text - css, javascript, image - image, png, jpeg, gif , video - mp4, avi)
    String mimeType = req.getServletContext().getMimeType(file.toString());

    // 브라우저는 모르는 mime type에 대해서는 다운로드 처리한다.
    // 보통 브라우저가 인지하는 ppt, xsl, word 확장자 파일도 강제로 다운로드 처리하고 싶을 때
    // application/octet-stream 를 마임타입으로 사용한다.
    if (mimeType == null) {
      // 강제로 이미지가 다운로드 되도록 처리한다. - 무조건 다운로드 되게 하려면 아래 마인타입을 사용한다.
      res.setContentType("application/octet-stream");
    }
    String downName = null;
    FileInputStream fis = null;
    ServletOutputStream sos = null;
    try {
      if (req.getHeader("user-agent").indexOf("MSIE") == -1) {
        downName = new String(b_file.getBytes("UTF-8"), "8859_1");
      } else {
        downName = new String(b_file.getBytes("EUC-KR"), "8859_1");
      }
      res.setHeader("Content-Disposition", "attachment;filename=" + downName);
      fis = new FileInputStream(file); // 파일객체를 활용해서 FileInputStream 객체를 생성한다.
      // 왜냐면 읽어온 이미지 정보(스트림 - stream)를 write해준다. -  다운로드 파일 내용까지도 볼 수 있다.

      // OutputStream에서는 사용자가 생성한 man2.png에 읽어들인 바이너리 코드들을 쓰기 해야 한다.
      sos = res.getOutputStream();
      byte b[] = new byte[1024 * 10];
      int data = 0;
      while ((data = (fis.read(b, 0, b.length))) != -1) {
        sos.write(b, 0, data);
      }
      // flush는 버퍼에 쌓여있는 정보를 내보낸다.
      // 영상파일은 너무 커 -> 가상 메모리에 미리 영상을 띄워둔다.
      // 패킷단위로 이동한다.
      sos.flush();
    } catch (Exception e) {
      log.info(e.toString());
    } finally {
      try {
        if (sos != null) sos.close();
        if (fis != null) fis.close();
      } catch (Exception e2) {
        // TODO: handle exception
      }
    }
    return null;
  }// end of imageGet

  /////////////////////////////////////////////////////////////////////////////////////////////

  /********************************************************************************************
   * 게시글 목록 조회 구현하기 - search | select | where | GET
   * URL매핑 이름 : boardList
   *********************************************************************************************/
  @GetMapping("board/boardList")
  public String boardList(@RequestParam Map<String, Object> pmap) {
    log.info("boardList호출 성공");
    String temp = "[{'b_title' : '글제목 입니다.', 'b_conent' : '글내용 입니다.'}]";
    List<Map<String, Object>> bList = null;
    bList = reBoardLogic.boardList(pmap);
    Gson g = new Gson();
    temp = g.toJson(bList);
    return temp;
  } // end of boardList

  /********************************************************************************************
   * 게시글 상세 조회 구현하기 - search | select | where | GET
   * URL매핑 이름 : boardDetail
   *********************************************************************************************/
  @GetMapping("board/boardDetail")
  public String boardDetail(@RequestParam Map<String, Object> pmap) {
    log.info("boardDetail호출 성공");
    List<Map<String, Object>> bList = null;

    // 전체 조회와 다른 부분이 조회수 업데이트 처리하기 + 댓글이 있을 때 포함시키기
    bList = reBoardLogic.boardDetail(pmap);
    Gson g = new Gson();
    String temp = null;
    temp = g.toJson(bList);
    return temp;
  } // end of boardDetail

  /********************************************************************************************
   * 게시글 등록 구현하기 - param(@RequestParam) | insert | POST | GET
   * URL매핑 이름 : boardInsert
   *
   * @return 1이면 등록 성공, 0이면 등록 실패
   *********************************************************************************************/
  @PostMapping("board/boardInsert")
  public String boardInsert(@RequestBody ReactBoard0206 board) {
    log.info("boardInsert호출 성공");
    log.info(board);
    int result = -1; // 초기값을 -1로 한 이유는 0과 1이 의미있는 숫자이다.
    result = reBoardLogic.boardInsert(board);
    return "" + result;
  }

  /********************************************************************************************
   * 게시글 수정 구현하기 - param |updata | where(한건수정하면되니까) | PK (프라이머리 키) | PUT
   * URL매핑 이름 : boardUpdate
   * update react_board set b_content=? ... where b_no=?
   * 주의 : 리액트에서 입력 받은 값을 받아오는 것이다. (자바에서 받는게 아니라)
   * body에 담겨서 전송된다.(b_no:1, b_title:"제목"....}
   *********************************************************************************************/
  @PutMapping("board/boardUpdate")
  public String boardUpdate(@RequestBody Map<String, Object> pmap) {
    log.info("boardUpdate호출 성공");
    int result = -1; // 초기값을 -1로 한 이유는 0과 1이 의미있는 숫자이다.
    result = reBoardLogic.boardUpdate(pmap);
    return "" + result; // "-1"
  }

  /********************************************************************************************
   * 게시글 삭제 구현하기 - PK | delete | where | DELETE
   * URL매핑 이름 : boardDelete
   *********************************************************************************************/
  @DeleteMapping("board/boardDelete")
  public String boardDelete(@RequestParam(value = "b_no", required = true) int b_no) {
    log.info("boardDelete호출 성공");
    int result = -1; // 초기값을 -1로 한 이유는 0과 1이 의미있는 숫자이다.
    result = reBoardLogic.boardDelete(b_no);
    return "" + result; // "-1"
  }

  /********************************************************************************************
   * 댓글 등록 구현하기 - insert | POST
   * URL매핑 이름 : commentInsert
   *********************************************************************************************/
  @PostMapping("board/commentInsert")
  public String commentInsert(@RequestBody Map<String, Object> pmap){
    int result = -1;
    result = reBoardLogic.commentInsert(pmap);
    return "" + result;
  } // end of commentInsert

  /********************************************************************************************
   * 댓글 수정 구현하기 - update | PUT
   * URL매핑 이름 : commentUpdate
   *********************************************************************************************/
  @PutMapping("board/commentUpdate")
  public String commentUpdate(@RequestBody Map<String, Object> pmap){
    int result = -1;
    result = reBoardLogic.commentUpdate(pmap);
    return "" + result;
  } // end of commentUpdate

  /********************************************************************************************
   * 댓글 삭제 구현하기 - delete | DELETE
   * URL매핑 이름 : commentDelete
   *********************************************************************************************/
  @DeleteMapping("board/commentDelete")
  public String commentDelete(@RequestParam(value = "bc_no", required = true) int bc_no){
    log.info("commentDelete 호출");
    int result = -1;
    result = reBoardLogic.commentDelete(bc_no);
    return String.valueOf(result);
  } // end of commentDelete
}
