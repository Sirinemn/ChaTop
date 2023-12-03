package com.openclassrooms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	
	@GetMapping("/users")
	public String getUser() {
		return "welcome user";
	}

}
