package com.rentRead.librarayManagement.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentRead.librarayManagement.model.Book;
import com.rentRead.librarayManagement.model.Rental;
import com.rentRead.librarayManagement.model.User;

public interface RentalRepositiory extends JpaRepository<Rental	, Long> {
	
	public List<Rental> findByUserAndReturned(User user, boolean returned);
	public Optional<Rental> findFirstByUserAndBookAndReturned(User user, Book book,boolean returned);
}
