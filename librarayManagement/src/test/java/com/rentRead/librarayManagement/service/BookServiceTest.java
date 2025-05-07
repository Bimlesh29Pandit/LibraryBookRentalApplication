package com.rentRead.librarayManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rentRead.librarayManagement.Exception.ResourceNotFoundException;
import com.rentRead.librarayManagement.dto.BookDto;
import com.rentRead.librarayManagement.model.Book;
import com.rentRead.librarayManagement.repo.BookRepositiory;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	@Mock
	ModelMapper modelMapper;
	@Mock
	BookRepositiory bookRepositiory;
	@InjectMocks
	BookService bookService;
	BookDto bookDto1,bookDto2;
	Book book1,book2;
	
	@BeforeEach
    void setUp() {
       

        bookDto1 = new BookDto(1L, "Book 1", "Author 1", "Genre 1", true);
        bookDto2 = new BookDto(2L, "Book 2", "Author 2", "Genre 2", true);
        book1 = new Book(1L, "Book 1", "Author 1", "Genre 1", true,5, new ArrayList<>());
        book2 = new Book(2L, "Book 2", "Author 2", "Genre 2", true,5, new ArrayList<>());
        
    }

	
	@Test
	public void getAllBoodTest() {
		List<BookDto> expectedBookDtos = List.of(bookDto1,bookDto2);
		List<Book> mockBook = List.of(book1, book2);
		
		Mockito.when(bookRepositiory.findAll()).thenReturn(mockBook);
		Mockito.when(modelMapper.map(book1, BookDto.class)).thenReturn(bookDto1);
		Mockito.when(modelMapper.map(book2, BookDto.class)).thenReturn(bookDto2);
		
		
		ResponseEntity<List<BookDto>> response = bookService.getAllBooks();
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(expectedBookDtos, response.getBody());
		
	}
	@Test
	public void getBookByIdTest() {
		Long bookId = book1.getId();
		
		
		Mockito.when(bookRepositiory.findById(bookId)).thenReturn(Optional.of(book1));
		Mockito.when(modelMapper.map(book1, BookDto.class)).thenReturn(bookDto1);
		
		ResponseEntity<BookDto> bookDto = bookService.getBookById(bookId);
		
		Assertions.assertEquals(HttpStatus.OK, bookDto.getStatusCode());
		Assertions.assertEquals(bookDto1, bookDto.getBody());
		
		
	}
	@Test
	public void getBookByIdNotFoundTest() {
		Long bookId = book1.getId();
		Mockito.when(bookRepositiory.findById(bookId)).thenReturn(Optional.empty());

		
		Assertions.assertThrows(ResourceNotFoundException.class, ()->bookService.getBookById(bookId));
		
	}
	@Test
	public void addBookTest() {
		
		Mockito.when(bookRepositiory.save(book1)).thenReturn(book1);
		Mockito.when(modelMapper.map(book1, BookDto.class)).thenReturn(bookDto1);
		
		ResponseEntity<BookDto> bookDto = bookService.addBook(book1);
		
		//expected, actual
		Assertions.assertEquals(HttpStatus.CREATED, bookDto.getStatusCode());
		Assertions.assertEquals(bookDto1,bookDto.getBody());
	}
	
	@Test
	public void updateBookTest() {
		
		Mockito.when(bookRepositiory.save(book1)).thenReturn(book1);
		Mockito.when(modelMapper.map(book1, BookDto.class)).thenReturn(bookDto1);
		
		ResponseEntity<BookDto> bookDto = bookService.updateBook(book1);
		
		Assertions.assertEquals(HttpStatus.CREATED, bookDto.getStatusCode());
		Assertions.assertEquals(bookDto1,bookDto.getBody());
	}
	@Test
	public void deleteBookTest() {
		Long bookId = book1.getId();
		
		Mockito.doNothing().when(bookRepositiory).deleteById(bookId);
		
		ResponseEntity<String> deletedBook = bookService.deleteBook(bookId);
		Assertions.assertEquals(HttpStatus.OK, deletedBook.getStatusCode());
		Assertions.assertEquals("Deleted Successfully", deletedBook.getBody());
	}
	

}

//public ResponseEntity<BookDto> updateBook(Book book) {
//	return addBook(book);
//}
//
//public ResponseEntity<String> deleteBook(Long bookId) {
//	bookRepositiory.deleteById(bookId);
//	return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
//}

//@Test
//public void getUserByIdNotFoundTest(){
//	String userId = "123";
//	Mockito.when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());
//	Assertions.assertThrows(UserNotFoundException.class,()->userService.getUserById(userId));
//}

//public ResponseEntity<BookDto> getBookById(Long bookId) {
//	// TODO Auto-generated method stub
//	Book book = bookRepositiory.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Invalid Book Id please try again !!!"));
//	BookDto bookDto = modelMapper.map(book, BookDto.class);
//	return new ResponseEntity<BookDto>(bookDto,HttpStatus.OK);
//}


