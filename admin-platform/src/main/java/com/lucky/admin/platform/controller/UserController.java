package com.lucky.admin.platform.controller;

import com.lucky.admin.platform.common.ApiParams;
import com.lucky.admin.platform.common.ApiResult;
import com.lucky.admin.platform.common.ApiResultBuilder;
import com.lucky.admin.platform.common.ApiResultCode;
import com.lucky.admin.platform.service.UserService;
import com.lucky.admin.platform.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/getSessionUserInfo")
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

	@RequestMapping("/reg")
	@ResponseBody
	public ApiResult regUser(@RequestBody ApiParams<User> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		User user = params.getData();
		if (userService.getUserByUsername(user.getUsername()) != null) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("该用户已存在！").build();
		}
		if (userService.createUser(user) > 0) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("注册成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("注册失败").build();
		}
	}

	@RequestMapping("/get")
	@ResponseBody
	public ApiResult getUser(@RequestBody ApiParams<String> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		User user = userService.getUserByUsername(params.getData());
		if (user == null) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("该用户不存在！").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(user).build();
		}
	}

	@RequestMapping("/chg")
	@ResponseBody
	public ApiResult chgUser(@RequestBody ApiParams<User> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		User user = params.getData();
		if (userService.getUserByUsername(user.getUsername()) == null) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("该用户不存在！").build();
		}
		if (userService.modifyUser(user) > 0) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("修改成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("修改失败").build();
		}
	}

	@RequestMapping("/chgPwd")
	@ResponseBody
	public ApiResult chgPwd(@RequestBody ApiParams<User> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		User user = params.getData();
		User chkUser = userService.getUserByUsername(user.getUsername());
		if (chkUser == null) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("该用户不存在！").build();
		}
		BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
		if (!encoder.matches(user.getPassword(), chkUser.getPassword())) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("原密码错误！").build();
		}
		user.setPassword(encoder.encode(user.getPassword().trim()));
		if (userService.modifyUser(user) > 0) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("修改成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("修改失败").build();
		}
	}
}
