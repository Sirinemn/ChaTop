package com.openclassrooms.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.dto.LoginDTO;
import com.openclassrooms.dto.UserDTO;
import com.openclassrooms.jwt.TokenProvider;
import com.openclassrooms.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
@Tag(name="User")
public class UserController {

	private final TokenProvider tokenProvider;
	private final UserService userService;
	private final String AUTHORIZATION_HEADER = "Authorization";

	private final AuthenticationManager authenticationManager;

	public UserController(TokenProvider tokenProvider, AuthenticationManager authenticationManager,
			UserService userService) {
		this.tokenProvider = tokenProvider;
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}

	@ApiOperation(value = "Return logged user", notes = "This method returns the logged user")
	@GetMapping("/me")
	@ResponseBody
	public ResponseEntity<UserDTO> currentUserName(Authentication authentication) {
		String name = authentication.getName();
		UserDTO user = userService.getUserByName(name);
		return ResponseEntity.ok(user);
	}

	@ApiOperation(value = "Find user", notes = "This method finds a user by id")
	@GetMapping("/user/{id}")
	public ResponseEntity<UserDTO> getUser(
			@ApiParam(name = "id", type = "Integer", value = "Id of the user", required = true) 
			@PathVariable int id) {
		UserDTO user = userService.getUser(id);
		return ResponseEntity.ok(user);
	}

	@ApiOperation(value = "Return token", notes = "This method returns the token after user is logged")
	@PostMapping("/login")
	public ResponseEntity<JWTToken> authorize(
			@ApiParam(name = "email", type = "String", value = "First Name of the user", required = true) 
			@Valid @RequestBody LoginDTO loginDTO) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDTO.getEmail(), loginDTO.getPassword());

		Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(authentication);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
	}
	@Hidden
	static class JWTToken {

		private String idToken;

		public JWTToken(String idToken) {
			this.idToken = idToken;
		}

		@JsonProperty("token")
		String getIdToken() {
			return idToken;
		}

		void setIdToken(String idToken) {
			this.idToken = idToken;
		}
	}

}
