package com.openclassrooms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.dto.RentalDto;
import com.openclassrooms.mapper.RentalMapper;
import com.openclassrooms.model.Rental;
import com.openclassrooms.model.RentalOwner;
import com.openclassrooms.model.UpdateRental;
import com.openclassrooms.model.User;
import com.openclassrooms.repository.RentalRepository;

@Service
public class RentalService {
	
	@Autowired
	private RentalMapper rentalMapper;
	
	private final RentalRepository rentalRepository;
	
	public RentalService(RentalRepository rentalRepository){
		this.rentalRepository= rentalRepository;
	}
    public RentalDto getRental(final int id){
    	Rental rental= rentalRepository.findById(id).orElse(null);
    	return rentalMapper.toDto(rental);
    }
    public UpdateRental getUpdateRental(final int id){
    	Rental rental= rentalRepository.findById(id).orElse(null);
    	return rentalMapper.toUpdate(rental);
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
	public void saveRental(String name,String description,float price, float surface, User user,String picture) {
		RentalOwner rentalOwner = new RentalOwner(name,price,surface,description,picture);
		Rental rental = rentalMapper.RantalOwnertoEntity(rentalOwner);
		rental.setOwner(user);
		rentalRepository.save(rental);	
	}
	public void updateRentalMethode(UpdateRental updateRental, final int id) {
		Rental rental =rentalRepository.findById(id).orElse(null);
		rental.setName(updateRental.getName());
		rental.setSurface(updateRental.getSurface());
		rental.setPrice(updateRental.getPrice());
		rental.setDescription(updateRental.getDescription());
	}
}