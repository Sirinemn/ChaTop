package com.openclassrooms.model;

import lombok.Data;

@Data
public class UpdateRental {
	
	private String name;
	private Float surface;
	private Float price;
	private String description;
	
	public UpdateRental() {
	}
	
}
