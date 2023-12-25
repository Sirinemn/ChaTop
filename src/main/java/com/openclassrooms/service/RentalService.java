package com.openclassrooms.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.dto.RentalDto;
import com.openclassrooms.mapper.RentalMapper;
import com.openclassrooms.model.Rental;
import com.openclassrooms.model.RentalOwner;
import com.openclassrooms.model.User;
import com.openclassrooms.repository.RentalRepository;

@Service
public class RentalService {
	
	private final String FOLDER_PATH ="D:/sirine/openclassrooms/projet_3/pictures/";
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

	public List<RentalDto> getRentals(){
		List<Rental> list= rentalRepository.findAll();
		List<RentalDto> dto= new ArrayList<>();
		list.forEach(elemnt -> dto.add(rentalMapper.toDto(elemnt)));
		return dto;
	}
	public void deleteRental(final int id) {
		rentalRepository.deleteById(id);
	}
	public void saveRental(String name, float surface,float price,String description, User user,MultipartFile file) throws IllegalStateException, IOException {
		String picture = FOLDER_PATH+file.getOriginalFilename().toString();
		RentalOwner rentalOwner = new RentalOwner(name,surface,price,description,picture);
		Rental rental = rentalMapper.RantalOwnertoEntity(rentalOwner);
		rental.setOwner(user);
		rentalRepository.save(rental);
	}
	public void updateRental(String name, float surface,float price,String description, final int id) {
		Rental rental = rentalRepository.findById(id).orElse(null);
		LocalDateTime now = LocalDateTime.now();
		rental.setName(name);
		rental.setSurface(surface);
		rental.setPrice(price);
		rental.setDescription(description);
		rental.setUpdatedAt(now);
		rentalRepository.save(rental);
	}
}