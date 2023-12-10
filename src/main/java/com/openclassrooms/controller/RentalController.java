package com.openclassrooms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.RentalDto;
import com.openclassrooms.model.Rental;
import com.openclassrooms.service.RentalService;

import io.swagger.annotations.Api;

@Api(value= "Gestion des locations")
@RestController
@RequestMapping("/api")

public class RentalController {
	
	private RentalService rentalService;
	public RentalController(RentalService rentalService) {
		this.rentalService=rentalService;
	}
	
	@GetMapping("/rentals")
	public ResponseEntity<List<RentalDto>> getRentals(){
		List<RentalDto> rentals= rentalService.getRentals();
		return ResponseEntity.ok(rentals);
	}
	@GetMapping("/rental/{id}")
	public Rental  getRental(@PathVariable int id){
		return rentalService.getRental(id);
	}
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<String> addRental(@RequestBody RentalDto rentalDto) {
		 rentalService.saveRental(rentalDto);
		 return new ResponseEntity<>("Added succeesfully", HttpStatus.CREATED);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/rental/{id}")
	public ResponseEntity<Void> deleteRental(@PathVariable int id){
		rentalService.deleteRental(id);
		return ResponseEntity.noContent().build();
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<Rental> updateRental(@PathVariable Rental rental, @PathVariable int id) {
		Rental result = rentalService.updateRental(rental,id);
		return ResponseEntity.ok().body(result);
	}

}
