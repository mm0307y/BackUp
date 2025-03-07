package com.example.demo.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.JwtAuthenticationResponse;
import com.example.demo.model.RefreshTokenRequest;
import com.example.demo.model.Role;
import com.example.demo.model.SigninRequest;
import com.example.demo.model.SignupRequest;
import com.example.demo.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDao userDao;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    //Spring에서는 빈을 관리해줌 - >  BeanFactory, ApplicationContext(권장)
    @Autowired
    private PasswordEncoder passwordEncoder;
    public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
        log.info("signin");
        //UsernamePasswordAuthenticationToken을 생성하여 사용자 이름과 비번을 전달함.
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
        (signinRequest.getUsername(), signinRequest.getPassword()));
        //인증이 성공하면 Dao를 통해 해당 사용자 정보를 데이터 베이스에서 조회함.
        //이 때 만일 사용자가 존재하지 않으면 IllegalArgumentException발생함. 
        User user = userDao.findByUsername(signinRequest.getUsername());
        Role role = user.getRole();
        String username = user.getUsername();
        String email = user.getEmail();
        int id = user.getId();
        String jwt = jwtService.generateToken(user);
        log.info(jwt);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        log.info(refreshToken);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setAccessToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setRole(role);
        jwtAuthenticationResponse.setUsername(username);
        jwtAuthenticationResponse.setId(id);
        jwtAuthenticationResponse.setEmail(email);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        log.info("refresh 호출");
        log.info(refreshTokenRequest);
        String userID = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userDao.findByUsername(userID);
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            String jwt = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setAccessToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);

            return jwtAuthenticationResponse;
        }
        return null;
    }
    //회원가입
    //주의사항 - 비번을 평문으로 처리하면 안됩니다.
    public int signup(SignupRequest signupRequest) {
        log.info("signup");
        int result = 0;
        //비밀번호 암호화 하기
        //passwordEncoder는 BCryptPasswordEncoder타입 인스턴스 변수 입니다.
        //개발자가 직접 인스턴스화 하지 않고 SecurityConfig.java에 등록 하였습니다.
        //빈을 등록할 때는 @Configuration과 @Bean 커플처럼 함께 사용됨.
        //날것의 비밀번호가 암호화된 비밀번호로 바뀌었다.
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        //User클래스에 사용자 정보가 있지만 이 클래스는 UserDetails타입의 클래스로 재정의하였다.
        //왜냐면 스프링 시큐리트에서는 인증 정보를 SecurityContext에 담아야 하는데 Authentication에 담을 수 
        //있는 타입이 Object이면 담을 수 없고 반드시 UserDetails타입만 담을 수  있다.
        //일반 세션을 관리하는 영역과 시큐리티가 사용하는 세션 영역이 다르다.
        result = userDao.signup(signupRequest);
        return result;
    }
}
