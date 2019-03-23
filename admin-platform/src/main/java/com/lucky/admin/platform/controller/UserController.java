package com.lucky.admin.platform.controller;

import com.lucky.admin.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userController")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/{id}/showUser")
	public String showUser(@PathVariable String id, HttpServletRequest request) {
		String u = userService.getUserById(id);
		request.setAttribute("user", u);
		return u;
	}

}
