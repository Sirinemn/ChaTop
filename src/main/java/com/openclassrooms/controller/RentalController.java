package com.openclassrooms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.model.Rental;
import com.openclassrooms.service.RentalService;

import io.swagger.annotations.Api;

@Api(value= "Gestion des locations")
@RestController
public class RentalController {
	
	@Autowired
	private RentalService rentalService;
	
	@GetMapping("/rentals")
	public Iterable<Rental> getRentals(){
		return rentalService.getRentals();
	}
	@GetMapping("/rental/{id}")
	public Optional<Rental> getRental(@RequestParam int id){
		return rentalService.getRental(id);
	}
	@PostMapping("/add")
	public Rental saveRental(@RequestBody Rental rental) {
		return rentalService.saveRental(rental);
	}
	@DeleteMapping("/rental/{id}")
	public void deleteRental(@RequestParam int id){
		rentalService.deleteRental(id);
	}
	@PutMapping("/update/{id}")
	public Rental updateRental(@RequestParam Rental rental, @RequestParam int id) {
		return rentalService.updateRental(rental,id);
	}

}
