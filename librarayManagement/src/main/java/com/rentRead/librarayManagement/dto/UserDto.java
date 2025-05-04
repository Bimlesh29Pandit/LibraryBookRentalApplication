package com.rentRead.librarayManagement.dto;

import java.util.ArrayList;
import java.util.List;

import com.rentRead.librarayManagement.enumAll.Role;
import com.rentRead.librarayManagement.model.Rental;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class UserDto {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Role role;
//	private List<RentalDto> rentals = new ArrayList<>();
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getFirstName() {
//		return firstName;
//	}
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//	public String getLastName() {
//		return lastName;
//	}
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public Role getRole() {
//		return role;
//	}
//	public void setRole(Role role) {
//		this.role = role;
//	}
//	public List<RentalDto> getRentals() {
//		return rentals;
//	}
//	public void setRentals(List<RentalDto> rentals) {
//		this.rentals = rentals;
//	}
//	public UserDto(Long id, String firstName, String lastName, String email, Role role, List<RentalDto> rentals) {
//		super();
//		this.id = id;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.role = role;
//		this.rentals = rentals;
//	}
//	public UserDto() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	@Override
//	public String toString() {
//		return "UserDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
//				+ ", role=" + role + ", rentals=" + rentals + "]";
//	}
	
	
	
}
