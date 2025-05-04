package com.rentRead.librarayManagement.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentRead.librarayManagement.dto.BookDto;
import com.rentRead.librarayManagement.model.Book;
import com.rentRead.librarayManagement.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	BookService bookService;
	@GetMapping
	public ResponseEntity<List<BookDto>> getAllBook(){
		
		return bookService.getAllBooks();
		
	}
	@GetMapping("/{bookId}")
	public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId){
		
		return bookService.getBookById(bookId);
		
	}
	@PostMapping
	public ResponseEntity<BookDto> addBook(@RequestBody Book book){
		return bookService.addBook(book);
	}
	@PutMapping
	public ResponseEntity<BookDto> updateBook(@RequestBody Book book){
		return bookService.updateBook(book);
	}
	
	@DeleteMapping("/{bookId}")
	public ResponseEntity<String> deleteBook(@PathVariable Long bookId){
		return bookService.deleteBook(bookId);
	}

}
