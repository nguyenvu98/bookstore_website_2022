package com.datn.utils;

import javax.servlet.http.HttpSession;

import com.datn.constant.SessionConstant;
import com.datn.dto.CartDto;
import com.datn.entity.Users;

public class SessionUtil {
	
	private SessionUtil() {}
	
	public static CartDto getCurrentCart(HttpSession session) {
		return (CartDto) session.getAttribute(SessionConstant.CURRENT_CART);
	}
	
	public static Users getCurrentUser(HttpSession session) {
		return (Users) session.getAttribute(SessionConstant.CURRENT_USER);
	}
}
