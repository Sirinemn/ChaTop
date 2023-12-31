package com.openclassrooms.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RentalDto {
	
	private Integer id;
	private String name;
	private Float surface;
	private Float price;
	private String picture;
	private String description;
	@JsonProperty("owner_id")
	private Integer ownerId;
	@JsonProperty("created_at")
	private LocalDateTime createdAt ;
	@JsonProperty("updated_at")
	private LocalDateTime updatedAt;

}
