package com.openclassrooms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.RentalDto;
import com.openclassrooms.mapper.RentalMapper;
import com.openclassrooms.model.Rental;
import com.openclassrooms.repository.RentalRepository;

@Service
public class RentalService {
	
	@Autowired
	private RentalMapper rentalMapper;
	
	private final RentalRepository rentalRepository;
	
	public RentalService(RentalRepository rentalRepository){
		this.rentalRepository= rentalRepository;
	}
    public Rental getRental(final int id){
    	return rentalRepository.findById(id).orElse(null);
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
	public void saveRental(RentalDto rentalDto) {
		Rental rental = rentalMapper.toEntity(rentalDto);
		rentalRepository.save(rental);
	}
	public Rental updateRental(Rental rental, final int id) {
		rental.setId(id);
		return rentalRepository.save(rental);
	}
	

}
