package com.openclassrooms.service;

import org.springframework.stereotype.Service;

import com.openclassrooms.dto.MessageDTO;
import com.openclassrooms.repository.MessageRepository;
import com.openclassrooms.repository.RentalRepository;
import com.openclassrooms.repository.UserRepository;
import com.openclassrooms.model.Message;
import com.openclassrooms.model.Rental;
import com.openclassrooms.model.User;

@Service
public class MessageService{
	
	private final RentalRepository rentalRepository;
	private final UserRepository userRepository;
	private MessageRepository messageRepository;
	public MessageService(MessageRepository messageRepository,RentalRepository rentalRepository,UserRepository userRepository) {
		this.messageRepository = messageRepository;
		this.rentalRepository = rentalRepository;
		this.userRepository = userRepository;
	}
	
	public void saveMessage(MessageDTO messageDto) {
		Message message = new Message();
		Integer userId = messageDto.getUserId();
		Integer rentalId = messageDto.getRentalId();
		User user = userRepository.findById(userId).orElse(null);
		Rental rental = rentalRepository.findById(rentalId).orElse(null);
		message.setRental(rental);
		message.setUser(user);
		message.setMessage(messageDto.getMessage());
		messageRepository.save(message);
	}

}
