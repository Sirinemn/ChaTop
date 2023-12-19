package com.openclassrooms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.dto.RentalDto;
import com.openclassrooms.model.RentalOwner;
import com.openclassrooms.model.RentalResponse;
import com.openclassrooms.model.RentalsResponse;
import com.openclassrooms.model.User;
import com.openclassrooms.service.RentalService;
import com.openclassrooms.service.UserService;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "http://localhost:4200")
@Api(value = "Gestion des locations")
@RestController
@RequestMapping("/api")

public class RentalController {

	private UserService userService;
	private RentalService rentalService;

	public RentalController(RentalService rentalService, UserService userService) {
		this.rentalService = rentalService;
		this.userService = userService;
	}

	@GetMapping("/rentals")
	public ResponseEntity<RentalsResponse> getRentals() {
		List<RentalDto> rentals = rentalService.getRentals();
		RentalsResponse rentalResponse = new RentalsResponse(rentals);
		return ResponseEntity.ok(rentalResponse);
	}

	@GetMapping("/rentals/{id}")
	public ResponseEntity<RentalDto> getRental(@PathVariable int id) {
		RentalDto rental = rentalService.getRental(id);
		return ResponseEntity.ok(rental);
	}

	@PostMapping("/rentals/add")
	public ResponseEntity<RentalResponse> addRental(@RequestBody RentalOwner rentalOwner,
			Authentication authentication) {
		String name = authentication.getName();
		User user = userService.getUserByname(name);
		rentalService.saveRental(rentalOwner, user);
		RentalResponse rentalResponse = new RentalResponse();
		rentalResponse.message = "Rental created !";
		return new ResponseEntity<>(rentalResponse, HttpStatus.CREATED);
	}

	public ResponseEntity<Void> deleteRental(@PathVariable int id) {
		rentalService.deleteRental(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<RentalResponse> updateRental(@RequestBody RentalOwner rentalOwner, @PathVariable int id) {
		rentalService.updateRental(rentalOwner, id);
		RentalResponse rentalResponse = new RentalResponse();
		rentalResponse.message = "Rental updated!";
		return new ResponseEntity<>(rentalResponse, HttpStatus.CREATED);

	}
}