package com.openclassrooms.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
@Table(name="rentals")
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private float surface;
	private float price;
	private String picture;
	private String description;
	private Integer owner_id;
	private LocalDateTime created_at ;
	private LocalDateTime updated_at ;
	
	//@ManyToOne
	//private User owner;


}
