package com.openclassrooms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.MessageDTO;
import com.openclassrooms.model.MessageResponse;
import com.openclassrooms.service.MessageService;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "http://localhost:4200")
@Api(value= "Ajouter un message")
@RestController
@RequestMapping("/api")
public class MessageController {
	
	private MessageService messageService;
	public MessageController(MessageService messageService) {
		this.messageService= messageService;	
	}
	
	@PostMapping("/messages")
	public ResponseEntity<MessageResponse> addMessage(@RequestBody MessageDTO messageDto) {
		 messageService.saveMessage(messageDto);
		 MessageResponse messageResponse = new MessageResponse("Message sent!");
		 return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
	}

}
