package com.api.springapiauth.controller;

import com.api.springapiauth.model.JwtRequest;
import com.api.springapiauth.model.JwtResponse;
import com.api.springapiauth.service.UserService;
import com.api.springapiauth.util.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {
  @Autowired
  private JWT jwt;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @PostMapping("/authenticate")
  public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            jwtRequest.getEmail(),
            jwtRequest.getPassword()
          )
        );
    } catch (BadCredentialsException e) {
        throw new Exception("Incorrect email or password", e);
    }

    final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());

    final String token = jwt.generateToken(userDetails);

    return new JwtResponse(token);
  }
}
