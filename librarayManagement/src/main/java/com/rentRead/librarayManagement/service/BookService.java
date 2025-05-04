package com.rentRead.librarayManagement.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rentRead.librarayManagement.Exception.ResourceNotFoundException;
import com.rentRead.librarayManagement.dto.BookDto;
import com.rentRead.librarayManagement.model.Book;
import com.rentRead.librarayManagement.repo.BookRepositiory;
@Service
public class BookService {


	@Autowired
	BookRepositiory bookRepositiory;
	@Autowired
	ModelMapper modelMapper;
	public ResponseEntity<List<BookDto>> getAllBooks() {
		// TODO Auto-generated method stub
		
		List<BookDto> bookDtos = bookRepositiory.findAll().stream().map(book->modelMapper.map(book,BookDto.class)).collect(Collectors.toList());
		return new ResponseEntity<List<BookDto>>(bookDtos,HttpStatus.OK);
	}
	public ResponseEntity<BookDto> addBook(Book book) {
		// TODO Auto-generated method stub

		Book bookAdd = bookRepositiory.save(book);
		BookDto bookDto = modelMapper.map(bookAdd, BookDto.class);
		
		return new ResponseEntity<BookDto>(bookDto,HttpStatus.CREATED);
	}
	public ResponseEntity<BookDto> updateBook(Book book) {
		// TODO Auto-generated method stub
		return addBook(book);
	}
	public ResponseEntity<String> deleteBook(Long bookId) {
		// TODO Auto-generated method stub
		bookRepositiory.deleteById(bookId);
		return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
	}
	public ResponseEntity<BookDto> getBookById(Long bookId) {
		// TODO Auto-generated method stub
		Book book = bookRepositiory.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Invalid Book Id please try again !!!"));
		BookDto bookDto = modelMapper.map(book, BookDto.class);
		return new ResponseEntity<BookDto>(bookDto,HttpStatus.OK);
	}

}
;