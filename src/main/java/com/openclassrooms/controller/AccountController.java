package com.openclassrooms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.RegisterDTO;
import com.openclassrooms.exception.UserAlreadyExistException;
import com.openclassrooms.model.User;
import com.openclassrooms.service.AccountService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AccountController {
	
	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) throws UserAlreadyExistException {
		User register=accountService.save(registerDto);
		if(register==null) return new ResponseEntity<>("Customer not created",HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>("Customer registered succcess", HttpStatus.CREATED);
	}

}
