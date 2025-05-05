package com.rentRead.librarayManagement.model;

import java.util.ArrayList;
import java.util.List;

import com.rentRead.librarayManagement.enumAll.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	@Column(nullable= false)
	private String firstName;
	private String lastName;
	
	@Column(nullable = false, unique =true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Rental> rentals = new ArrayList<>();
	

}
