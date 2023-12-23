package com.openclassrooms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MessageDTO {
	
	@JsonProperty("rental_id")
	private Integer rentalId;
	@JsonProperty("user_id")
	private Integer userId;
	private String message;

}
