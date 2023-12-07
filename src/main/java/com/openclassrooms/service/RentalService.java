package com.openclassrooms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.model.Rental;
import com.openclassrooms.repository.RentalRepository;

@Service
public class RentalService {
	@Autowired
	private RentalRepository rentalRepository;
	
    public Optional<Rental> getRental(final int id){
    	return rentalRepository.findById(id);
    }
	public Iterable<Rental> getRentals(){
		return rentalRepository.findAll();
	}
	public void deleteRental(final int id) {
		rentalRepository.deleteById(id);
	}
	public Rental saveRental(Rental rental) {
		Rental savedRental = rentalRepository.save(rental);
		return savedRental;
	}
	public Rental updateRental(Rental rental, final int id) {
		rental.setId(id);
		return rentalRepository.save(rental);
	}
	

}
