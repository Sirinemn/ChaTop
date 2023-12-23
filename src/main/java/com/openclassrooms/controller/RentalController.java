package com.openclassrooms.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.openclassrooms.dto.RentalDto;
import com.openclassrooms.model.RentalResponse;
import com.openclassrooms.model.RentalsResponse;
import com.openclassrooms.model.UpdateRental;
import com.openclassrooms.model.User;
import com.openclassrooms.service.RentalService;
import com.openclassrooms.service.UserService;

import io.swagger.annotations.Api;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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

	@PostMapping(value="/rentals/add",consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<RentalResponse> addRental(@RequestPart("picture") MultipartFile file,
	        @RequestParam("name") @NotBlank @Size(max=63) String name,
	        @RequestParam("surface") @Min(0) float surface,
	        @RequestParam("price") @Min(0) float price,
	        @RequestParam("description") @Size(max=2000) String description,
			Authentication authentication) {
		String userName = authentication.getName();
		User user = userService.getUserByname(userName);
		RentalResponse rentalResponse = new RentalResponse();
		try {
		String picture = file.getInputStream().toString();
		rentalService.saveRental(name,description,price,surface,user,picture);
		rentalResponse.message = "Rental created !";
		return new ResponseEntity<>(rentalResponse, HttpStatus.CREATED);
		}catch(Exception e) {
			rentalResponse.message = "Something went wrong !";
			return new ResponseEntity<>(rentalResponse,HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("rentals/delete")
	public ResponseEntity<Void> deleteRental(@PathVariable int id) {
		rentalService.deleteRental(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/rentals/update/{id}")
	public ResponseEntity<RentalResponse> updateRental( @RequestParam("name") @NotBlank @Size(max=63) String name,
	        @RequestParam("surface") @Min(0) float surface,
	        @RequestParam("price") @Min(0) float price,
	        @RequestParam("description") @Size(max=2000) String description, @PathVariable int id) throws IOException{
		UpdateRental updateRental = rentalService.getUpdateRental(id);
		updateRental.setName(name);
		updateRental.setDescription(description);
		updateRental.setPrice(price);
		updateRental.setSurface(surface);
		rentalService.updateRentalMethode(updateRental, id);
		RentalResponse rentalResponse = new RentalResponse("Rental updated!");
		return new ResponseEntity<>(rentalResponse, HttpStatus.OK);

	}
}