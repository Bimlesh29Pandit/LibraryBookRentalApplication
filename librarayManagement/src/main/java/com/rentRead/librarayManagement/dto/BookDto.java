package com.rentRead.librarayManagement.dto;

import java.util.ArrayList;
import java.util.List;

import com.rentRead.librarayManagement.model.Rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class BookDto {

	private Long id;
	private String title;
	private String author;
	private String genre;
//	private int available_quantity;
	private boolean avilable_status;

//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
//	public String getAuthor() {
//		return author;
//	}
//	public void setAuthor(String author) {
//		this.author = author;
//	}
//	public String getGenre() {
//		return genre;
//	}
//	public void setGenre(String genre) {
//		this.genre = genre;
//	}
//	public int getAvailable_quantity() {
//		return available_quantity;
//	}
//	public void setAvailable_quantity(int available_quantity) {
//		this.available_quantity = available_quantity;
//	}
//	//	public List<Rental> getRentals() {
//	//		return rentals;
//	//	}
//	//	public void setRentals(List<Rental> rentals) {
//	//		this.rentals = rentals;
//	//	}
//	//	public BookDto(Long id, String title, String author, String genre, int available_quantity, List<Rental> rentals) {
//	//		super();
//	//		this.id = id;
//	//		this.title = title;
//	//		this.author = author;
//	//		this.genre = genre;
//	//		this.available_quantity = available_quantity;
//	//		this.rentals = rentals;
//	//	}
//
//	public BookDto() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public BookDto(Long id, String title, String author, String genre, int available_quantity) {
//		super();
//		this.id = id;
//		this.title = title;
//		this.author = author;
//		this.genre = genre;
//		this.available_quantity = available_quantity;
//	}
//	//	@Override
//	//	public String toString() {
//	//		return "BookDto [id=" + id + ", title=" + title + ", author=" + author + ", genre=" + genre
//	//				+ ", available_quantity=" + available_quantity + ", rentals=" + rentals + "]";
//	//	}
//	@Override
//	public String toString() {
//		return "BookDto [id=" + id + ", title=" + title + ", author=" + author + ", genre=" + genre
//				+ ", available_quantity=" + available_quantity + "]";
//	}




}
