package com.example.demo.controller0205;

import com.example.demo.model0206.KakaoProfile0213;
import com.example.demo.model0206.OAuthToken0213;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

// 8000번 서버와 카카오 서버의 관계
@Log4j2
@RestController
@RequestMapping("/auth/*")
public class KakaoAuthController0212 {
  // spring5에서 RestTemplate가 deprecated 대상이다.
  private WebClient webClient = WebClient.builder().build();
  // redirect_url -> http://localhost:8000/auth/kakao/callback

  @GetMapping("kakao/callback")
  public String kakaoCallback(String code, HttpSession session, HttpServletResponse res) throws Exception {
    // 1단계 : 인가코드 확인하기 - 리액트 서버에서 처리하였고 카카오가 8000번에 응답을 보냈다.
    // 테스트 시나리오 - 리액트 화면에서 카카오 로그인 버튼 클릭 -> 카카오 정해준 인가코드를 받아오는 URL이 제공된다.
    log.info("code : " + code);

    // 2단계 - Access Token 요청하기 - 실제로 스프링이 처리하는 구간은 2단계 구간부터 입니다.
    // 우리는 화면이 없는 상태에서 (postman이 없는)post 요청을 해야 한다.
    // POST방식으로 key=value 형태로 데이터를 요청(카카오 쪽으로)
    // Retrofit2 or OkHttp 라이브러리 - 스프링이 제공하지 않는다.
    RestTemplate restTemplate = new RestTemplate();
    // POST방식으로 전송할 때는 Header설정이 필요하다.
    // 헤더 정보에 필요한 값을 넣어서 보내려면 헤더객체를 직접 생성해야 한다.
    HttpHeaders headers = new HttpHeaders();

    // 마임타입을 아래와 같이 하면 내가 전송할 데이터가 key와 value 형태라는 걸 알려주는 코드이다.
    headers.add("Content-Type", "application/x-www-form-urlencoded");
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("grant_type", "authorization_code");
    map.add("client_id", "4ea7ace8a82124712ffdfd528e65bcf5");
    map.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
    map.add("code", code);
    // HttpHeader와 HttpBody를 하나의 오브젝트로 담기
    HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(map, headers);

    // Http요청을 POST로 하기 -> 그리고 response객체로 응답을 받아온다.
    ResponseEntity<String> response = restTemplate.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST, tokenRequest, String.class);
    // access_token값이 있으면 카카오 리소스 서버에 접근할 수 있다.
    System.out.println(response.getBody());

    ObjectMapper objectMapper = new ObjectMapper();
    OAuthToken0213 oAuthToken = null;
    try {
      oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken0213.class);
    }catch (JsonMappingException jme){
      jme.printStackTrace();
    }catch (JsonProcessingException jpe){
      jpe.printStackTrace();
    }
    // 카카오로부터 발급 받은 토큰
    log.info("access_token : " + oAuthToken.getAccess_token());

    // 위에서 발급 받은 토큰을 가지고 카카오 리소스 서버에 프로필 정보 다시 요청하기
    RestTemplate rt2 = new RestTemplate();
    HttpHeaders headers2 = new HttpHeaders();
    headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
    headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

    // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    HttpEntity<MultiValueMap<String, String>> tokenRequest2 = new HttpEntity<>(headers2);
    ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, tokenRequest2, String.class);
    System.out.println("개인 정보 : " + response2.getBody()); // 한글 정보 포함
    ObjectMapper objectMapper2 = new ObjectMapper();
    KakaoProfile0213 kakaoProfile = null;
    try{
      // KakaoProfile 클래스에 담는다.
      kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile0213.class);
    }catch (JsonMappingException jme){
      jme.printStackTrace();
    }catch (JsonProcessingException jpe){
      jpe.printStackTrace();
    }
    log.info("kakaoProfile : " + kakaoProfile); // 카카오 리소스서버에서 전송한 정보 덩어리
    log.info("카카오 프로파일 ID : " + kakaoProfile.id);
    log.info("profile_image" + kakaoProfile.properties.profile_image);
    log.info("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
    log.info("카카오 유저네임 : " + kakaoProfile.getProperties().nickname); // 한글이름

    String nickname = null;
    res.sendRedirect("http://localhost:3000/login-success?email="+kakaoProfile.getKakao_account().getEmail());

    // 카카오 프로필을 세션에 저장하기
    session.setAttribute("s_email", kakaoProfile.kakao_account.getEmail());
    return null;
  } // end of KakaoCallback

  @GetMapping("logout")
  public String logout(HttpSession session) {
    // session.removeAttribute("s_email") 하나씩 삭제할 때 사용한다.
    // 세션에 저장된 모든 값을 한 번에 다 삭제하기
    session.invalidate();
    return "logout";
  }
}
