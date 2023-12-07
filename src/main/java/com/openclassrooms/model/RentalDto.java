package com.openclassrooms.model;

import org.modelmapper.ModelMapper;

import lombok.Data;

@Data
public class RentalDto {
	private Integer id;
	private String name;
	private float surface;
	private float price;
	private String picture;
	private String description;
	
/*	public RentalDto convertToDto(Rental rental) {
		 RentalDto rentalDto = ModelMapper.map(rental,RentalDto.class);
		 rentalDto.id=rental.getId();
		 rentalDto.name=rental.getName();
		 rentalDto.surface=rental.getSurface();
		 rentalDto.price=rental.getPrice();
		 rentalDto.picture=rental.getPicture();
		 rentalDto.description=rental.getDescription();
		 return rentalDto;
		 
	}*/

}
