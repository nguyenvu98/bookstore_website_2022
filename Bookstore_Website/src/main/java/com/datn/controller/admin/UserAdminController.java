package com.datn.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.datn.entity.Users;
import com.datn.service.UsersService;

@Controller
//@RequestMapping("/user")
public class UserAdminController {

	@Autowired
	private UsersService usersService;
	
	@GetMapping("admin/user")
	public String getUsers(Model model) {
		List<Users> users = usersService.findAll();
		model.addAttribute("account", users);
		model.addAttribute("userRequest", new Users());
		return "admin/users";
	}
	
	
	@PostMapping("admin/user/edit")
	public String doPostUpdateUser(@ModelAttribute("userRequest")Users userRequest,
			RedirectAttributes redirectAttributes) {
		try {
			usersService.update(userRequest);
			redirectAttributes.addFlashAttribute("succeedMessage", "User " + userRequest.getUsername() + " have been eidted !");
			return "redirect:/admin/user";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Cannot update user");		
		}
		return "redirect:/admin/user";
	}
	
	@GetMapping("admin/user/edit")
	public String doGetEditUser(@RequestParam("username")String username, Model model) {
		Users userRequest = usersService.findByUsername(username);
		model.addAttribute("userRequest", userRequest);
		return "admin/users::#form";
	}
	
	
	@GetMapping("admin/user/delete")
	public String doGetDelete(@RequestParam("username")String username,
			RedirectAttributes redirectAttributes) {
		try {
			usersService.deleteLogical(username);
			redirectAttributes.addFlashAttribute("succeedMessage", "User " + username + " was deleted !");
			return "redirect:/admin/user";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete user");
		}
		return "redirect:/admin/user";

	}
	
	@PostMapping("admin/user/create")
	public String doPostCreateUser(@ModelAttribute("userRequest")Users userRequest,
			RedirectAttributes redirectAttributes) {
		try {
			usersService.save(userRequest);
			redirectAttributes.addFlashAttribute("succeedMessage", "User " + userRequest.getUsername() + " was created !");
			return "redirect:/admin/user";
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Cannot create user");		
		}
		return "redirect:/admin/user";
	}

}
