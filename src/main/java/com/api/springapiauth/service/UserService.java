package com.api.springapiauth.service;

import java.util.ArrayList;

import com.api.springapiauth.repository.UserRepository;

import com.api.springapiauth.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    final User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UsernameNotFoundException("Not found!");
    }

    return new org.springframework.security.core.userdetails.User(
      user.getEmail(),
      user.getPassword(),
      new ArrayList<>()
    );
  }
}
