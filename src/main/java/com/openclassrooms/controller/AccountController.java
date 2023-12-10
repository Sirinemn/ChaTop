package com.openclassrooms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.RegisterDTO;
import com.openclassrooms.exception.UserAlreadyExistException;
import com.openclassrooms.service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountController {
	
	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) throws UserAlreadyExistException {
		accountService.save(registerDto);	
		return new ResponseEntity<>("user registered succcess", HttpStatus.CREATED);
	}

}
