package com.example.demo.config0217.auth0217;

import com.example.demo.model0217.User0217;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
 로그인 진행이 완료되면 시큐리티가 가지고 있는 session을 만들어 준다.
 같은 세션공간인데 시큐리티만의 공간(Security ContextHolder에 키를 저장)이
 따로 있다. - 아무 클래스나 담을 수 없다. 다시 말해 UserDetails타입만 담을 수 있다.
 오브젝트라고 해서 다 들어갈 수 없다.
 User오브젝트 타입 -> UserDetails 타입 객체이어야 한다.
 Security Session영역이 따로 있는데 들어갈 수 있는 객체는 Authentication만 들어갈 수 있다.
 이 말은 Authentication안에 User정보가 있어야 한다.
 Authentication에서 UserDetails를 꺼낼 수 있다.
 Authentication객체는 PrincipalDetailsService에서 만들어 준다.

*/

public class PrincipalDetails0217 implements UserDetails {
	private User0217 user;
	public PrincipalDetails0217(User0217 user) {
		this.user = user;
	}

	// 해당하는 User권한을 리턴하는 곳이다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole(); // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
			}
		});
		return collection;
	} // end of getAuthorities()

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 너 휴먼 계정이야?
	@Override
	public boolean isEnabled() {
		// 우리 사이트에 1년 동안 회원이 로그인을 안하면 휴먼계정으로 할 때 추가코드가 필요하다.
		// 현재 시간 - 마지막 로깅시간 -> 만일 1년을 초과했다면 false로 만들어준다.
		return true;
	}
}
