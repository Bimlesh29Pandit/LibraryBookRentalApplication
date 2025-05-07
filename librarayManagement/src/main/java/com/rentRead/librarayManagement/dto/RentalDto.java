package com.rentRead.librarayManagement.dto;

import java.time.LocalDateTime;

import com.rentRead.librarayManagement.model.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
	
	private Long id;
//	private UserDto user;
	private BookDto book;
	private LocalDateTime rentedAt = LocalDateTime.now();
	private boolean returned = false;
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public UserDto getUser() {
//		return user;
//	}
//	public void setUser(UserDto  user) {
//		this.user = user;
//	}
//	public BookDto getBook() {
//		return book;
//	}
//	public void setBook(BookDto book) {
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
//	public RentalDto(Long id, UserDto user, BookDto book, LocalDateTime rentedAt, boolean returned) {
//		super();
//		this.id = id;
//		this.user = user;
//		this.book = book;
//		this.rentedAt = rentedAt;
//		this.returned = returned;
//	}
//	public RentalDto() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	@Override
//	public String toString() {
//		return "RentalDto [id=" + id + ", user=" + user + ", book=" + book + ", rentedAt=" + rentedAt + ", returned="
//				+ returned + "]";
//	}
//	
	
}
