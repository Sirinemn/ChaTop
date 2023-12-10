package com.openclassrooms.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.UserDTO;
import com.openclassrooms.mapper.UserMapper;
import com.openclassrooms.model.User;
import com.openclassrooms.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	private UserRepository userRepository;
	public UserService(UserRepository userRepository) {
		this.userRepository= userRepository;
	}
	
	public UserDTO getUser(final int id) {
		User user = userRepository.findById(id).orElse(null);
		UserDTO dto = userMapper.toDto(user);
		return dto;
		
	}
}
