package com.datn.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datn.constant.SessionConstant;
import com.datn.entity.ProductTypes;
import com.datn.entity.Products;
import com.datn.entity.Users;
import com.datn.service.ProductTypeService;
import com.datn.service.ProductsService;
import com.datn.service.UsersService;

@Controller
public class HomeController {
	
	@Autowired
	private ProductsService productsService;
	
	@Autowired
	private UsersService usersService;
	
	
	@Autowired
	private ProductTypeService productTypeService;
	
	private static final int MAX_SIZE = 4;
	
	@GetMapping(value = {"/", ""})
	public String doGetRedirectIndex() {
		return "redirect:/index";
	}
	
	@GetMapping("/index")
	public String doGetId(Model model) {
		List<Products> products = productsService.findAll();
		model.addAttribute("products", products);
		List<ProductTypes> listTypes = productTypeService.findAll();
		model.addAttribute("cates", listTypes);
		return "user/index";
	}
//	
//	@GetMapping("/index")
//	public String doGetIndex(@RequestParam(value = "page", required = false, defaultValue = "0")int page,
//			Model model) {
//		List<Products> products = new ArrayList<>();
//		try {
//			Page<Products> pageProduct = productsService.findAll(MAX_SIZE,page);
//			products = pageProduct.getContent();
//			model.addAttribute("totalPages",pageProduct.getTotalPages());
//			model.addAttribute("currentPage",page);
//
//		} catch (Exception e) {
//			 products = productsService.findAll();
//		}
//		List<Products> products = productsService.findAll();
//		model.addAttribute("products", products);
//		List<ProductTypes> listTypes = productTypeService.findAll();
//		model.addAttribute("cates", listTypes);
//		return "user/index";
//	}
	
	@GetMapping("/product/detail/{id}")
	public String doGetId(Model model, 
			@PathVariable("id")Long id ) {
		Products item = productsService.findById(id);
		model.addAttribute("item", item);
		return "user/detail";
	}
	
	@GetMapping("/detail")
	public String doGetId1(Model model) {
		return "user/detail";
	}
	
	
	@GetMapping("/login")
	public String doGetLogin(Model model) {
		model.addAttribute("userRequest", new Users());
		return "user/login";
	}
	
	@GetMapping("/logout")
	public String doGetLogout(HttpSession session) {
		session.removeAttribute(SessionConstant.CURRENT_USER);
		return "redirect:/index";
	}
	
	@GetMapping("/register")
	public String doGetRegister(Model model) {
		model.addAttribute("userRequest", new Users());
		return "user/register";
	}
	
	@PostMapping("/login")
	public String doPostLogin(@ModelAttribute("userRequest") Users userRequest, HttpSession session) {
		Users userResponse = usersService.doLogin(userRequest);
		if (ObjectUtils.isNotEmpty(userResponse)) {
			session.setAttribute(SessionConstant.CURRENT_USER, userResponse);
			return "redirect:/index";
		}
		return "redirect:/login";
	}
	
	@PostMapping("/register")
	public String doPostRegister(@ModelAttribute("userRequest") Users userRequest, HttpSession session) {
		try {
			Users userResponse = usersService.save(userRequest);
			if (ObjectUtils.isNotEmpty(userResponse)) {
				session.setAttribute(SessionConstant.CURRENT_USER, userResponse);
				return "redirect:/index";
			} else {
				return "redirect:/register";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "redirect:/register";
		}
	}
	

}
