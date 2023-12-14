package com.openclassrooms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.RentalDto;
import com.openclassrooms.mapper.RentalMapper;
import com.openclassrooms.model.Rental;
import com.openclassrooms.model.RentalOwner;
import com.openclassrooms.model.User;
import com.openclassrooms.repository.RentalRepository;
import com.openclassrooms.repository.UserRepository;

@Service
public class RentalService {
	
	@Autowired
	private RentalMapper rentalMapper;
	
	private final RentalRepository rentalRepository;
	private final UserRepository userRepository;
	
	public RentalService(RentalRepository rentalRepository, UserRepository userRepository){
		this.rentalRepository= rentalRepository;
		this.userRepository= userRepository;
	}
    public RentalDto getRental(final int id){
    	Rental rental= rentalRepository.findById(id).orElse(null);
    	return rentalMapper.toDto(rental);
    }
	public List<RentalDto> getRentals(){
		List<Rental> list= rentalRepository.findAll();
		List<RentalDto> dto= new ArrayList<>();
		list.forEach(elemnt -> dto.add(rentalMapper.toDto(elemnt)));
		return dto;
	}
	public void deleteRental(final int id) {
		rentalRepository.deleteById(id);
	}
	public void saveRental(RentalOwner rentalOwner) {
		Rental rental = new Rental();
		String ownerName= rentalOwner.getOwnerName();
		User owner = userRepository.findByName(ownerName).get();
		rental.setName(rentalOwner.getName());
		rental.setSurface(rentalOwner.getSurface());
		rental.setPrice(rentalOwner.getPrice());
		rental.setPicture(rentalOwner.getPicture());
		rental.setDescription(rentalOwner.getDescription());
		rental.setOwner(owner);
		rentalRepository.save(rental);
		
	}
	public void updateRental(RentalOwner rentalOwner, final int id) {
		Rental rental = rentalRepository.findById(id).get();
		String ownerName= rentalOwner.getOwnerName();
		User owner = userRepository.findByName(ownerName).get();
		rental.setName(rentalOwner.getName());
		rental.setSurface(rentalOwner.getSurface());
		rental.setPrice(rentalOwner.getPrice());
		rental.setPicture(rentalOwner.getPicture());
		rental.setDescription(rentalOwner.getDescription());
		rental.setOwner(owner);
		rental.setUpdatedAt(java.time.LocalDateTime.now());
		rentalRepository.save(rental);
	}
	

}
