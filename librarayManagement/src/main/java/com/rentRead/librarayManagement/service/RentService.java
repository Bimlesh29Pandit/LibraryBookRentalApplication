package com.rentRead.librarayManagement.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rentRead.librarayManagement.Exception.ResourceNotFoundException;
import com.rentRead.librarayManagement.dto.BookDto;
import com.rentRead.librarayManagement.dto.RentalDto;
import com.rentRead.librarayManagement.model.Book;
import com.rentRead.librarayManagement.model.Rental;
import com.rentRead.librarayManagement.model.User;
import com.rentRead.librarayManagement.repo.BookRepositiory;
import com.rentRead.librarayManagement.repo.RentalRepositiory;
import com.rentRead.librarayManagement.repo.UserRepositiory;

import jakarta.servlet.http.HttpServletRequest;
@Service
public class RentService {
	@Autowired
	HttpServletRequest request;
	@Autowired
	JwtService jwtService;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	BookRepositiory bookRepositiory;
	@Autowired
	UserRepositiory userRepositiory;
	@Autowired
	RentalRepositiory rentalRepositiory;

	public ResponseEntity<RentalDto> rentBook(Long bookId) {
		Book book = bookRepositiory.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

		if (!book.isAvailable_status() || book.getAvailable_quantity() <= 0) {
			throw new IllegalArgumentException("Book is currently not available for rent.");
		}

		// Extract user from JWT token
		String token = request.getHeader("Authorization").substring(7);
		String userEmail = jwtService.extractUserName(token);
		User user = userRepositiory.findByEmail(userEmail);

		if (user == null) {
			throw new ResourceNotFoundException("User not found for email: " + userEmail);
		}

		// Check if user already rented this book and hasn't returned it
		Optional<Rental> existingRental = rentalRepositiory.findFirstByUserAndBookAndReturned(user, book, false);
		if (existingRental.isPresent()) {
			throw new IllegalArgumentException("You have already rented this book and haven't returned it.");
		}

		// Check how many books the user currently has rented
		List<Rental> activeRentals = rentalRepositiory.findByUserAndReturned(user, false);
		if (activeRentals.size() >= 2) {
			throw new IllegalArgumentException("You already have 2 rented books. Return one to rent a new book.");
		}

		// Rent the book
		Rental rental = new Rental();
		rental.setBook(book);
		rental.setUser(user);
		rental.setRentedAt(LocalDateTime.now());
		rental.setReturned(false);
		Rental savedRental = rentalRepositiory.save(rental);

		// Update book inventory
		int updatedQuantity = book.getAvailable_quantity() - 1;
		book.setAvailable_quantity(updatedQuantity);
		book.setAvailable_status(updatedQuantity > 0);
		bookRepositiory.save(book);

		RentalDto rentalDto = modelMapper.map(savedRental, RentalDto.class);
		return new ResponseEntity<>(rentalDto, HttpStatus.CREATED);
	}




	public ResponseEntity<RentalDto> returnBook(Long bookId) {
		Book book = bookRepositiory.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

		String token = request.getHeader("Authorization").substring(7);
		String userEmail = jwtService.extractUserName(token);

		User user = userRepositiory.findByEmail(userEmail);
		if (user == null) {
			throw new ResourceNotFoundException("User not found for email: " + userEmail);
		}

		Rental rental = rentalRepositiory.findFirstByUserAndBookAndReturned(user, book, false)
				.orElseThrow(() -> new IllegalArgumentException("No active rental found for this book and user"));

		// Mark the book as returned
		rental.setReturned(true);
		rentalRepositiory.save(rental);

		// Update book availability
		book.setAvailable_quantity(book.getAvailable_quantity() + 1);
		book.setAvailable_status(true);
		bookRepositiory.save(book);

		RentalDto rentalDto = modelMapper.map(rental, RentalDto.class);
		return ResponseEntity.ok(rentalDto);
	}




	public ResponseEntity<List<RentalDto>> rentHistory() {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization").substring(7);
		String userEmail = jwtService.extractUserName(token);

		User user = userRepositiory.findByEmail(userEmail);
		if (user == null) {
			throw new ResourceNotFoundException("User not found for email: " + userEmail);
		}
		
		List<Rental> rentals = rentalRepositiory.findByUser(user);
		List<RentalDto> rentalDtos = rentals.stream().map(rental->modelMapper.map(rental, RentalDto.class)).collect(Collectors.toList());
		
		
		return new ResponseEntity<List<RentalDto>>(rentalDtos,HttpStatus.OK);
	}




	public ResponseEntity<List<RentalDto>> activeRentHistory(boolean active) {
		// TODO Auto-generated method stub
		String token = request.getHeader("Authorization").substring(7);
		String userEmail = jwtService.extractUserName(token);

		User user = userRepositiory.findByEmail(userEmail);
		if (user == null) {
			throw new ResourceNotFoundException("User not found for email: " + userEmail);
		}
		
		List<Rental> rentals = rentalRepositiory.findByUserAndReturned(user,active);
		List<RentalDto> rentalDtos = rentals.stream().map(rental->modelMapper.map(rental, RentalDto.class)).collect(Collectors.toList());
		
		
		return new ResponseEntity<List<RentalDto>>(rentalDtos,HttpStatus.OK);
	}


}
