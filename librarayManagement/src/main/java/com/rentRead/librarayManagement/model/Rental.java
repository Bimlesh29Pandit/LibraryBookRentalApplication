package com.rentRead.librarayManagement.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private User user;
	@ManyToOne
	private Book book;
	private LocalDateTime rentedAt = LocalDateTime.now();
	private boolean returned = false;
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
//	public Book getBook() {
//		return book;
//	}
//	public void setBook(Book book) {
//		this.book = book;
//	}
//	public LocalDateTime getRentedAt() {
//		return rentedAt;
//	}
//	public void setRentedAt(LocalDateTime rentedAt) {
//		this.rentedAt = rentedAt;
//	}
//	public boolean isReturned() {
//		return returned;
//	}
//	public void setReturned(boolean returned) {
//		this.returned = returned;
//	}
//	public Rental(Long id, User user, Book book, LocalDateTime rentedAt, boolean returned) {
//		super();
//		this.id = id;
//		this.user = user;
//		this.book = book;
//		this.rentedAt = rentedAt;
//		this.returned = returned;
//	}
//	
//	public Rental() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	@Override
//	public String toString() {
//		return "Rental [id=" + id + ", user=" + user + ", book=" + book + ", rentedAt=" + rentedAt + ", returned="
//				+ returned + "]";
//	}




}
