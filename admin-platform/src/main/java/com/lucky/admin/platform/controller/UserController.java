package com.lucky.admin.platform.controller;

import com.lucky.admin.platform.common.ApiResult;
import com.lucky.admin.platform.common.ApiResultBuilder;
import com.lucky.admin.platform.common.ApiResultCode;
import com.lucky.admin.platform.service.UserService;
import com.lucky.admin.platform.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/getSessionUserInfo")
	@ResponseBody
	public ApiResult getSessionUserInfo() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal  != null && principal instanceof UserDetails) {
			User user = (User) principal;
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(user).build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.DataNotExist.code()).msg(ApiResultCode.DataNotExist.msg()).build();
		}
	}

	@RequestMapping("/{username}/showUser")
	public String showUser(@PathVariable String username, HttpServletRequest request) {
		String user = userService.getUserByUsername(username);
		request.setAttribute("user", user);
		return user;
	}
}
