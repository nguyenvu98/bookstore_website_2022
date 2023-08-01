package com.datn.service;

import com.datn.entity.Roles;

public interface RolesService {
	
	Roles findByDescription(String description);
}
