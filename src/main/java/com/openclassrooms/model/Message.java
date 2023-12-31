package com.openclassrooms.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="messages")
public class Message {
	
	public Message() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String message;
	@Column(name= "created_at")
	private LocalDateTime createdAt;
	@Column(name= "updated_at")
	private LocalDateTime updatedAt;
	
	@ManyToOne
	@JoinColumn(name= "user_id")
    private User user;
	
	@ManyToOne
	@JoinColumn(name= "rental_id")
    private Rental rental;

}
