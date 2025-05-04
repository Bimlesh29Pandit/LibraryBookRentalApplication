package com.rentRead.librarayManagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentRead.librarayManagement.model.User;
@Repository
public interface UserRepositiory extends JpaRepository<User, Long>{
	
	public User findByEmail(String email);
	public boolean existsByEmail(String email);

}
