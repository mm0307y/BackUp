package com.example.demo.logic0207;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao0207.ReBoardDao0207;
import com.example.demo.model0206.ReactBoard0206;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReBoardLogic0207 {

  @Autowired
  private ReBoardDao0207 reBoardDao = null; // 절대로 new 하면 안된다. - 빈관리를 받지 않는다.

  public List<Map<String, Object>> boardList(Map<String, Object> pmap) {
    log.info("boardList 호출 성공.");
    List<Map<String, Object>> bList = null;
    bList = reBoardDao.boardList(pmap);
    return bList;
  }

  public String imageUpload(MultipartFile image) {
    Map<String, Object> pmap = new HashMap<>();
    String savePath = "D:\\Java\\workspace_board\\mvc-board\\src\\main\\webapp\\pds0207";
    String fileName = null;
    String fullPath = null;

    // 이미지 파일이 존재하면 처리한다.
    // 같은 이름의 파일이 존재하면 안되니까 예방하는 코드 작성
    if (image != null && !image.isEmpty()) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      Calendar time = Calendar.getInstance();
      fileName = sdf.format(time.getTime()) + "-" + image.getOriginalFilename().replaceAll("", "");
      fullPath = savePath + "\\" + fileName;

      try {
        // file명을 클래스로 만들어 주는 클래스 File - 파일이름을 객체화 될 뿐 내용은 없다.
        File file = new File(fullPath); // 여기서 만들어진 파일은 이름만 존재하고 내용은 깡통
        // 이미지 파일은 메모장으로 볼 수 없다. - 왜냐면 문자와 숫자로 되어 있는 바이너리 파일이라서...
        byte[] bytes = image.getBytes(); // 스프링에서 3000번 서버에서 요청으로 파일 내용을 읽기
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bos.write(bytes); // 이미지 파일 안에 바이너리 코드를 채운다.
        bos.close(); // IO는 누수가 되지 않도록, 인터셉트 당하지 않도록 닫아준다.

        // 파일 처리하는 경우 - 추가 파일 정보가 필요할 때 - 배제
        // 파일 크기
        double size = Math.floor(file.length() / (1024.0 * 1024.0) * 10) / 10;
        log.info("size : " + size);
        pmap.put("file_name", fileName);
        pmap.put("file_size", size);
        pmap.put("file_path", fullPath);

      } catch (Exception e) {
        log.info(e.toString());
      }
    }
    return fileName;
  }

  @GetMapping("board/imageGet")
  public String imageGet(HttpServletRequest req, HttpServletResponse res) {
    String b_file = req.getParameter("imageName");
    log.info("imageGet 호출 성공 ==> " + b_file);
    String filePath = "D:\\Java\\workspace_board\\mvc-board\\src\\main\\webapp\\pds0207"; // 절대 경로
    String fname = b_file;
    log.info("b_file: 8->euc"+b_file);
    File file = new File(filePath,b_file.trim());
    String mimeType = req.getServletContext().getMimeType(file.toString());
    if(mimeType == null){
      res.setContentType("application/octet-stream");
    }
    String downName = null;
    FileInputStream fis = null;
    ServletOutputStream sos = null;
    try{
      if(req.getHeader("user-agent").indexOf("MSIE")==-1){
        downName = new String(b_file.getBytes("UTF-8"),"8859_1");
      }else{
        downName = new String(b_file.getBytes("EUC-KR"),"8859_1");
      }
      res.setHeader("Content-Disposition", "attachment;filename="+downName);
      fis = new FileInputStream(file);
      sos = res.getOutputStream();
      byte b[] = new byte[1024*10];
      int data = 0;
      while((data=(fis.read(b,0,b.length)))!=-1){
        sos.write(b,0,data);
      }
      sos.flush();
    }catch(Exception e){
      log.info(e.toString());
    }finally{
      try {
        if(sos != null) sos.close();
        if(fis != null) fis.close();
      } catch (Exception e2) {
        // TODO: handle exception
      }
    }
    return null;
  } // end of imageGet
  public int boardInsert(ReactBoard0206 board){
    int result = -1;
    result = reBoardDao.boardInsert(board);
    return result;
  }

  public List<Map<String, Object>> boardDetail(Map<String, Object> pmap) {
    // 1. 상세조회를 한다.(where b_no = 5)
    // 2. 상세조회가 되면 조회수를 1증가 시킨다.
    // 3. 댓글이 존재하면 댓글을 목록에 포함 시킨다.
    List<Map<String, Object>> bList = null;
    bList = reBoardDao.boardList(pmap);
    if (bList.size() == 1) { // size()가 0이면 조회결과가 없고 1이면 조회결과가 있다.
      reBoardDao.hitCount(pmap);
    }

    // 댓글 가져오기
    List<Map<String, Object>> commList = reBoardDao.commentList(pmap);
    if (commList != null && commList.size() > 0) {
      Map<String, Object> cmap = new HashMap<>();
      cmap.put("comments", commList);
      bList.add(1, cmap);
    }
    return bList;
  } // end of boardDetail

  public int boardUpdate(Map<String, Object> pmap) {
    log.info("boardUpdate 호출");
    int result = -1;
    result = reBoardDao.boardUpdate(pmap);
    return result;
  } // end of boardUpdate

  public int boardDelete(int b_no) {
    int result = -1;
    result = reBoardDao.boardDelete(b_no);
    log.info(result);
    return result;
  } // end of boardDelete

  public int commentInsert(Map<String, Object> pmap) {
    log.info("commentInsert");
    int result = -1;
    result = reBoardDao.commentInsert(pmap);
    return result;
  } // end of commentInsert

  public int commentUpdate(Map<String, Object> pmap) {
    log.info("commentUpdate");
    int result = -1;
    result = reBoardDao.commentUpdate(pmap);
    return result;
  }

  public int commentDelete(int bc_no) {
    log.info("commentDelete");
    int result = -1;
    // pk와 fk 기억 - 조인에서 사용 - 조인 조건으로 사용된다.
    // react_board : b_no, react_board_comment : bc_np(pk, b_no-fk)
    // pk컬럼들은 index전략 - 대용량 데이터 베이스 솔루션 - ERD - DBA
    result = reBoardDao.commentDelete(bc_no);
    return result;
  }
}