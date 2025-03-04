package com.example.demo.service0304;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao0304.UserDao0304;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserService0304 {
  @Autowired
  private UserDao0304 userDao;

  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {

      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        return userDao.findByUsername(username);
      }

    };
  }
}
