package com.openclassrooms.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.dto.RentalDto;
import com.openclassrooms.mapper.RentalMapper;
import com.openclassrooms.model.Rental;
import com.openclassrooms.model.RentalOwner;
import com.openclassrooms.model.User;
import com.openclassrooms.repository.RentalRepository;

@Service
public class RentalService {
	
	private final String IMAGE_PATH ="D:/sirine/openclassrooms/projet_3/pictures";

	private final RentalMapper rentalMapper;

	private final RentalRepository rentalRepository;

	public RentalService(RentalRepository rentalRepository, RentalMapper rentalMapper) {
		this.rentalRepository = rentalRepository;
		this.rentalMapper = rentalMapper;
	}

	public RentalDto getRental(final int id) {
		Rental rental = rentalRepository.findById(id).orElse(null);
		return rentalMapper.toDto(rental);
	}

	public List<RentalDto> getRentals() {
		List<Rental> list = rentalRepository.findAll();
		List<RentalDto> dto = new ArrayList<>();
		list.forEach(rental -> dto.add(rentalMapper.toDto(rental)));
		return dto;
	}

	public void deleteRental(final int id) {
		rentalRepository.deleteById(id);
	}

	public void saveRental(String name, float surface, float price, String description, User user, MultipartFile file)
			throws IllegalStateException, IOException {
		String fileName =file.getOriginalFilename().toString();
		String picture = IMAGE_PATH+"/" + fileName;
		saveFile(fileName,file);
		RentalOwner rentalOwner = new RentalOwner(name, surface, price, description, picture);
		Rental rental = rentalMapper.RantalOwnertoEntity(rentalOwner);
		rental.setOwner(user);
		rentalRepository.save(rental);
	}

	public void updateRental(String name, float surface, float price, String description, final int id) {
		Rental rental = rentalRepository.findById(id).orElse(null);
		LocalDateTime now = LocalDateTime.now();
		rental.setName(name);
		rental.setSurface(surface);
		rental.setPrice(price);
		rental.setDescription(description);
		rental.setUpdatedAt(now);
		rentalRepository.save(rental);
	}
    public static String saveFile(String fileName, MultipartFile multipartFile)
            throws IOException {
        Path uploadPath = Paths.get("Files-Upload");
          
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
 
        String fileCode = RandomStringUtils.randomAlphanumeric(8);
         
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {       
            throw new IOException("Could not save file: " + fileName, ioe);
        }
        return fileCode;
    }  
        
}