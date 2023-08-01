package com.datn.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.datn.entity.Users;
import com.datn.service.StatsService;
import com.datn.service.UsersService;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private StatsService statsService;
	
	@Autowired
	private UsersService usersService;
	
	@GetMapping("")
	public String doGetIndex(Model model) {
		String chartData[][] = statsService.getTotalPriceLast6Months();
		model.addAttribute("chartData", chartData);
		return "admin/index";
	}
	
	/*
	 * @GetMapping("/admin/users") public String getUsers(Model model) { List<Users>
	 * users = usersService.findAll(); model.addAttribute("account", users); return
	 * "admin/users"; }
	 */
}
