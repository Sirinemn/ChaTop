package com.openclassrooms.mapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.dto.RentalDto;
import com.openclassrooms.model.Rental;

@Component
public class RentalMapper {
	
	
	public RentalDto toDto(Rental entity) {
		RentalDto dto = new RentalDto();
	    dto.setId(entity.getId());
	    dto.setName(entity.getName());
	    dto.setSurface(entity.getSurface());
	    dto.setPrice(entity.getPrice());
	    dto.setPicture(entity.getPicture());
	    dto.setDescription(entity.getDescription());

	    return dto;
	  }
	  
	  public Rental toEntity(RentalDto dto) {
		  Rental entity = new Rental();
	    entity.setId(dto.getId());
	    entity.setName(dto.getName());
	    entity.setSurface(dto.getSurface());
	    entity.setPrice(dto.getPrice());
	    entity.setPicture(dto.getPicture());
	    entity.setDescription(dto.getDescription());
	    return entity;
	  }

}
