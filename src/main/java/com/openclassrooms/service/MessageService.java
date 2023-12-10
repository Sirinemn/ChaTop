package com.openclassrooms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.MessageDTO;
import com.openclassrooms.mapper.MessageMapper;
import com.openclassrooms.repository.MessageRepository;
import com.openclassrooms.model.Message;

@Service
public class MessageService{
	
	@Autowired
	private MessageMapper messageMapper;
	
	private MessageRepository messageRepository;
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public void saveMessage(MessageDTO messageDto) {
		Message message = messageMapper.toEntity(messageDto);
		messageRepository.save(message);
	}

}
