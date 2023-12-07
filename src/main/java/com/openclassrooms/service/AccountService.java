package com.openclassrooms.service;

import com.openclassrooms.dto.RegisterDTO;
import com.openclassrooms.exception.UserAlreadyExistException;

public interface AccountService {
	void save(RegisterDTO registerDto) throws UserAlreadyExistException;

}
