package com.datn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.datn.service.UsersService;

@Controller
public class SecurityController {
	@Autowired
	UsersService usersService;
	
	@RequestMapping("/security/login")
	public String loginForm(Model model) {
		model.addAttribute("message", "Vui lòng đăng nhập");
		return "user/login1";
	}
	
	@RequestMapping("/security/login/success")
	public String loginSuccess(Model model) {
		model.addAttribute("message", "Đăng nhập thành công");
		return "user/index";
	}
	
	@RequestMapping("/security/login/error")
	public String loginError(Model model) {
		model.addAttribute("message", "Sai thông tin đăng nhập");
		return "user/login1";
	}
	
	@RequestMapping("/security/unauthoried")
	public String unauthoried(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất");
		return "user/login1";
	}
	
	@RequestMapping("/security/logoff/success")
	public String logoffSuccess(Model model) {
		model.addAttribute("message", "Bạn đã đăng xuất");
		return "user/login1";
	}
}
