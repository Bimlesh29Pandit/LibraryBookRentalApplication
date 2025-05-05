package com.rentRead.librarayManagement.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
		// TODO Auto-generated method stub

		Book book = bookRepositiory.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Rent for this bookId is not available try again with diffrent bookId"));
		if(book.isAvailable_status()) {

			String authHeader = request.getHeader("Authorization");
			String token = authHeader.substring(7);
			String userName = jwtService.extractUserName(token);
			User user = userRepositiory.findByEmail(userName);
			List<Rental> activeRentals = rentalRepositiory.findByUserAndReturned(user,false);
			if(activeRentals.size()>=2) {
				throw new IllegalArgumentException("You have already rented 2 books. Return one to rent a new book.");
			}
			Rental rental = new Rental();
			rental.setBook(book);
			rental.setUser(user);
			rental.setRentedAt(LocalDateTime.now());
			rental.setReturned(false);
			Rental savedRental = rentalRepositiory.save(rental);
			
			
			
			//update the book
			book.setAvailable_quantity(book.getAvailable_quantity()-1);
			if(book.getAvailable_quantity()<=0) {
				book.setAvailable_status(false);
			}
			bookRepositiory.save(book);
			
			RentalDto rentalDto = modelMapper.map(savedRental, RentalDto.class);
			return new ResponseEntity<>(rentalDto, HttpStatus.CREATED);

		}
		else {
			throw new IllegalArgumentException("Book is not Available Currently Try after Sometime");

		}

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


}
