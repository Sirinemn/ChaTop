package com.openclassrooms.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.dto.RegisterDTO;
import com.openclassrooms.jwt.JWTConfigurer;
import com.openclassrooms.jwt.TokenProvider;
import com.openclassrooms.model.Role;
import com.openclassrooms.model.User;
import com.openclassrooms.repository.RoleRepository;
import com.openclassrooms.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private final TokenProvider tokenProvider;
	
	private PasswordEncoder passwordEncoder;
	
	private UserRepository userRepository;
	
	private RoleRepository roleRepository;


    private final AuthenticationManager authenticationManager;
	
	public UserController(TokenProvider tokenProvider,AuthenticationManager authenticationManager) {
		this.tokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;

	}
	
	@PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody Login login) {
	
	   UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
 
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

	   static class JWTToken {

	        private String idToken;

			public JWTToken(String idToken) {
				this.idToken = idToken;
			}

			@JsonProperty("id_token")
	        String getIdToken() {
	            return idToken;
	        }

	        void setIdToken(String idToken) {
	            this.idToken = idToken;
	        }
	    }
	   @PostMapping("register")
	   public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto){
		   if (userRepository.existsByName(registerDto.getName())) {
			   return new ResponseEntity<>("username is taken!",HttpStatus.BAD_REQUEST);
		   }
		   User user = new User();
		   user.setName(registerDto.getName());
		   user.setEmail(registerDto.getEmail());
		   user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		   
		   Role role = roleRepository.findByName("USER").get();
		   user.setRoles(Collections.singletonList(role));
		   userRepository.save(user);
		   return new ResponseEntity<>("user registered succcess",HttpStatus.OK);
		   
	   }

}
