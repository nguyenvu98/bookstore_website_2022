package com.datn.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.datn.constant.RolesConstant;
import com.datn.entity.Roles;
import com.datn.entity.Users;
import com.datn.repo.UsersRepo;
import com.datn.service.RolesService;
import com.datn.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
	
	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	@Autowired
	private UsersRepo repo;
	
	@Autowired
	private RolesService rolesService;

	@Override
	public Users doLogin(Users userRequest) {
		Users userResponse = repo.findByUsername(userRequest.getUsername());
		
		if (ObjectUtils.isNotEmpty(userResponse)) {
			boolean checkPassword = bcrypt.matches(userRequest.getHashPassword(), userResponse.getHashPassword());
			return checkPassword ? userResponse : null;
		}
		
		return null;
	}

	@Override
	@Transactional(rollbackOn = {Throwable.class})
	public Users save(Users user) throws SQLException {
		Roles role = rolesService.findByDescription(RolesConstant.USER);
		user.setRole(role);
		user.setIsDeleted(Boolean.FALSE);
		
		String hashPassword = bcrypt.encode(user.getHashPassword());
		user.setHashPassword(hashPassword);
		
		return repo.saveAndFlush(user);
	}

	@Override
	public List<Users> findAll() {
		return repo.findAll();
	}

	@Override
	public Users findByUsername(String username) {
		return repo.findByUsername(username);
	}

	@Transactional(rollbackOn = {Exception.class, Throwable.class})
	@Override
	public void deleteLogical(String username) {
		repo.deleteLogical(username);
	}

	@Override
	@Transactional(rollbackOn = {Exception.class, Throwable.class})
	public void update(Users user) {
		if(ObjectUtils.isNotEmpty(user) && StringUtils.isEmpty(user.getHashPassword())) {
			repo.updateNonPass(user.getEmail(), user.getFullname(), user.getUsername());
		}else {
			String hashPassword = bcrypt.encode(user.getHashPassword());
			user.setHashPassword(hashPassword);
			repo.update(user.getEmail(), hashPassword, user.getFullname(), user.getUsername());
		}
	}
}
