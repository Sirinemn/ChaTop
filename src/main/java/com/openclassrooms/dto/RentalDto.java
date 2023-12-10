package com.openclassrooms.dto;


import lombok.Data;

@Data
public class RentalDto {
	public Integer id;
	public String name;
	public float surface;
	public float price;
	public String picture;
	public String description;

}
