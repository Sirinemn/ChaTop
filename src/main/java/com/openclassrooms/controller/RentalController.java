package com.openclassrooms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.RentalDto;
import com.openclassrooms.model.RentalOwner;
import com.openclassrooms.service.RentalService;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "http://localhost:4200")
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
	@GetMapping("/rentals/{id}")
	public RentalDto  getRental(@PathVariable int id){
		return rentalService.getRental(id);
	}
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/rentals/add")
	public ResponseEntity<String> addRental(@RequestBody RentalOwner rentalOwner) {
		 rentalService.saveRental(rentalOwner);
		 return new ResponseEntity<>("Rental created !", HttpStatus.CREATED);
	}
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteRental(@PathVariable int id){
		rentalService.deleteRental(id);
		return ResponseEntity.noContent().build();
	}
	//@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateRental(@RequestBody RentalOwner rentalOwner, @PathVariable int id) {
	    rentalService.updateRental(rentalOwner, id);
		return new ResponseEntity<>("Rental updated !", HttpStatus.CREATED);
	}

}
