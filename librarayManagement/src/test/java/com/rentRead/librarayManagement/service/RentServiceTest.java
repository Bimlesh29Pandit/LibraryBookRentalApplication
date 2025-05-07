package com.rentRead.librarayManagement.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rentRead.librarayManagement.dto.BookDto;
import com.rentRead.librarayManagement.dto.RentalDto;
import com.rentRead.librarayManagement.enumAll.Role;
import com.rentRead.librarayManagement.model.Book;
import com.rentRead.librarayManagement.model.Rental;
import com.rentRead.librarayManagement.model.User;
import com.rentRead.librarayManagement.repo.BookRepositiory;
import com.rentRead.librarayManagement.repo.RentalRepositiory;
import com.rentRead.librarayManagement.repo.UserRepositiory;

import jakarta.servlet.http.HttpServletRequest;


@ExtendWith(MockitoExtension.class)
public class RentServiceTest {
	@Mock
	HttpServletRequest request;
	@Mock
	JwtService jwtService;
	@Mock
	ModelMapper modelMapper;
	@Mock
	BookRepositiory bookRepositiory;
	@Mock
	UserRepositiory userRepositiory;
	@Mock
	RentalRepositiory rentalRepositiory;
	
	@InjectMocks
	RentService rentService;
	
	Book book1;
	BookDto bookDto1;
	User user;
	Rental rental1;	
	RentalDto rentalDto1;
	@BeforeEach
	void runEach() {
		 user = new User(1L, "Rashi", "Yadav", "Rashi@example.com", "abc", Role.USER, new ArrayList<>());
		 bookDto1 = new BookDto(1L, "Book 1", "Author 1", "Genre 1", true);
	     book1 = new Book(1L, "Book 1", "Author 1", "Genre 1", true,5, new ArrayList<>());
	     rental1 = new Rental(1L,user,book1,LocalDateTime.now(),false);
	     rentalDto1 = new RentalDto(1L,bookDto1,LocalDateTime.now(),false);
	}
	
	@Test
	public void rentBookTest() {
		Long bookId = book1.getId();
		String userEmail = user.getEmail();
		String token = "Bearer dummyToken";
		
		when(bookRepositiory.findById(bookId)).thenReturn(Optional.of(book1));
		when(request.getHeader("Authorization")).thenReturn(token);
	    when(jwtService.extractUserName("dummyToken")).thenReturn(userEmail);
		when(userRepositiory.findByEmail(userEmail)).thenReturn(user);
		when(rentalRepositiory.findFirstByUserAndBookAndReturned(user, book1, false)).thenReturn(Optional.empty());
		when(rentalRepositiory.findByUserAndReturned(user, false)).thenReturn(new ArrayList<Rental>());
		when(rentalRepositiory.save(any(Rental.class))).thenReturn(rental1);
//		doNothing().when(bookRepositiory).save(book1);
		when(modelMapper.map(rental1, RentalDto.class)).thenReturn(rentalDto1);
		
		
		ResponseEntity<RentalDto> rentalDto =  rentService.rentBook(bookId);
		
		Assertions.assertEquals(HttpStatus.CREATED, rentalDto.getStatusCode());
		Assertions.assertEquals(rentalDto1, rentalDto.getBody());
		Assertions.assertEquals(4, book1.getAvailable_quantity());
		Assertions.assertTrue(book1.isAvailable_status());
		
	}

}

//public ResponseEntity<RentalDto> rentBook(Long bookId) {
//	Book book = bookRepositiory.findById(bookId)
//			.orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));
//
//	if (!book.isAvailable_status() || book.getAvailable_quantity() <= 0) {
//		throw new IllegalArgumentException("Book is currently not available for rent.");
//	}
//
//	// Extract user from JWT token
//	String token = request.getHeader("Authorization").substring(7);
//	String userEmail = jwtService.extractUserName(token);
//	User user = userRepositiory.findByEmail(userEmail);
//
//	if (user == null) {
//		throw new ResourceNotFoundException("User not found for email: " + userEmail);
//	}
//
//	// Check if user already rented this book and hasn't returned it
//	Optional<Rental> existingRental = rentalRepositiory.findFirstByUserAndBookAndReturned(user, book, false);
//	if (existingRental.isPresent()) {
//		throw new IllegalArgumentException("You have already rented this book and haven't returned it.");
//	}
//
//	// Check how many books the user currently has rented
//	List<Rental> activeRentals = rentalRepositiory.findByUserAndReturned(user, false);
//	if (activeRentals.size() >= 2) {
//		throw new IllegalArgumentException("You already have 2 rented books. Return one to rent a new book.");
//	}
//
//	// Rent the book
//	Rental rental = new Rental();
//	rental.setBook(book);
//	rental.setUser(user);
//	rental.setRentedAt(LocalDateTime.now());
//	rental.setReturned(false);
//	Rental savedRental = rentalRepositiory.save(rental);
//
//	// Update book inventory
//	int updatedQuantity = book.getAvailable_quantity() - 1;
//	book.setAvailable_quantity(updatedQuantity);
//	book.setAvailable_status(updatedQuantity > 0);
//	bookRepositiory.save(book);
//
//	RentalDto rentalDto = modelMapper.map(savedRental, RentalDto.class);
//	return new ResponseEntity<>(rentalDto, HttpStatus.CREATED);
//}