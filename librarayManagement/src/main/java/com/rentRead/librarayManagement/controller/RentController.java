package com.rentRead.librarayManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentRead.librarayManagement.dto.RentalDto;
import com.rentRead.librarayManagement.service.RentService;

@RestController
@RequestMapping("/books")
public class RentController {
	
	@Autowired
	RentService rentService;
	@PostMapping("/{bookId}/rent")
	public ResponseEntity<RentalDto> rentBook(@PathVariable Long bookId){
		
		return rentService.rentBook(bookId);
		
	}
	@PostMapping("/{bookId}/return")
	public ResponseEntity<RentalDto> returnBook(@PathVariable Long bookId){
		return rentService.returnBook(bookId);
	}

	@GetMapping("/rent/history")
	public ResponseEntity<List<RentalDto>> rentHistory(@RequestParam(required = false) Boolean active) {
	    if (active == null) {
	        return rentService.rentHistory(); // return all
	    } else {
	        return rentService.activeRentHistory(active); // true or false
	    }
	}


}
