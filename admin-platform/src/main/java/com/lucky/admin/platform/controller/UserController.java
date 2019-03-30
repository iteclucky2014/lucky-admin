package com.lucky.admin.platform.controller;

import com.lucky.admin.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/{username}/showUser")
	public String showUser(@PathVariable String username, HttpServletRequest request) {
		String user = userService.getUserByUsername(username);
		request.setAttribute("user", user);
		return user;
	}

}
