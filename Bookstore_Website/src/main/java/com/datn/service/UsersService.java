package com.datn.service;

import java.sql.SQLException;
import java.util.List;

import com.datn.entity.Users;

public interface UsersService {
	List<Users> findAll();
	Users doLogin(Users userRequest);
	Users save(Users user) throws SQLException;
	
	Users findByUsername(String username);

	void deleteLogical(String username);
	
	void update(Users users);
}
