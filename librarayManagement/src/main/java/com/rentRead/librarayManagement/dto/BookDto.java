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
@AllArgsConstructor
public class BookDto {

	private Long id;
	private String title;
	private String author;
	private String genre;
	private boolean avilable_status;





}
