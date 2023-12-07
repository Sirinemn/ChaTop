package com.openclassrooms.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.model.Rental;


public interface RentalRepository extends JpaRepository<Rental,Integer>{
	Rental findByName(String name);


}
