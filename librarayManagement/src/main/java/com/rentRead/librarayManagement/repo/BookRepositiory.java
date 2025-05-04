package com.rentRead.librarayManagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentRead.librarayManagement.model.Book;

@Repository
public interface BookRepositiory extends JpaRepository<Book, Long> {

}
