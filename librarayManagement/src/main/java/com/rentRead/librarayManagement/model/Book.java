package com.rentRead.librarayManagement.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor

public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String author;
	private String genre;
	private boolean available_status ;
	private int available_quantity;
	
	@OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
	private List<Rental> rentals = new ArrayList<>();
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
//	
//	public String getGenre() {
//		return genre;
//	}
//	public void setGenre(String genre) {
//		this.genre = genre;
//	}
//	public boolean isAvailable_status() {
//		return available_status;
//	}
//	public void setAvailable_status(boolean available_status) {
//		this.available_status = available_status;
//	}
//	public int getAvailable_quantity() {
//		return available_quantity;
//	}
//	public void setAvailable_quantity(int available_quantity) {
//		this.available_quantity = available_quantity;
//	}
//	public List<Rental> getRentals() {
//		return rentals;
//	}
//	public void setRentals(List<Rental> rentals) {
//		this.rentals = rentals;
//	}
//	public Book(Long id, String title, String author, String genre, boolean available_status, int available_quantity,
//			List<Rental> rentals) {
//		super();
//		this.id = id;
//		this.title = title;
//		this.author = author;
//		this.genre = genre;
//		this.available_status = available_status;
//		this.available_quantity = available_quantity;
//		this.rentals = rentals;
//	}
//	
//	public Book() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	@Override
//	public String toString() {
//		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", genre=" + genre + ", available_status="
//				+ available_status + ", available_quantity=" + available_quantity + ", rentals=" + rentals + "]";
//	}
	
	
	

}
