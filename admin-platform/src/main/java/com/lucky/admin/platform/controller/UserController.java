package com.lucky.admin.platform.controller;

import com.lucky.admin.platform.common.*;
import com.lucky.admin.platform.service.UserService;
import com.lucky.admin.platform.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/getSessionUserInfo")
	@ResponseBody
	public ApiResult getSessionUserInfo() throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal  != null && principal instanceof UserDetails) {
			User user = (User) principal;
			user.setAvatar(userService.getUserByUsername(user.getUsername()).getAvatar());
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(user).build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.DataNotExist.code()).msg(ApiResultCode.DataNotExist.msg()).build();
		}
	}

	@RequestMapping("/reg")
	@ResponseBody
	public ApiResult regUser(@RequestBody ApiParams<User> params) throws Exception {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		User user = params.getData();
		user.setRoleId(4);
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
	public ApiResult getUser(@RequestBody ApiParams<User> params) throws Exception {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		User user = userService.getUserByUsername(params.getData().getUsername());
		if (user == null) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("该用户不存在！").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(user).build();
		}
	}

	@RequestMapping("/getUsers")
	@ResponseBody
	public ApiResult getUserByCondition(@RequestParam(required = false) Long page, @RequestParam(required = false) Long limit,
	                                    @RequestParam(required = false) String username, @RequestParam(required = false) String nickname,
                                        @RequestParam(required = false) String sex, @RequestParam(required = false) String mobile,
                                        @RequestParam(required = false) String email, @RequestParam(required = false) String createTimeFrom,
                                        @RequestParam(required = false) String createTimeTo, @RequestParam(required = false) String isDelete) {
		if (page == null || page.longValue() == 0L || limit == null || limit.longValue() == 0L) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		User user = new User();
        user.setPage(page);
        user.setLimit(limit);
        user.setUsername(username);
        user.setNickname(nickname);
        user.setSex(sex);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setCreateTimeFrom(createTimeFrom);
        user.setCreateTimeTo(createTimeTo);
        user.setIsDelete(isDelete);
		user.setRoleId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getRoleId());
		Long count = userService.getUserCountByCondition(user);
//		if (count == null || count.longValue() == 0L) {
//			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("数据不存在！").build();
//		}
		ApiPagination pagination = new ApiPagination(page, limit, count);
		user.setOffset(pagination.getOffset());
		List<User> users = userService.getUserByCondition(user);
		return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(users).pagination(pagination).build();
	}

	@RequestMapping("/chg")
	@ResponseBody
	public ApiResult chgUser(@RequestBody ApiParams<User> params) throws Exception {
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
	public ApiResult chgPwd(@RequestBody ApiParams<User> params) throws Exception {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		User user = params.getData();
		User chkUser = userService.getUserByUsername(user.getUsername());
		if (chkUser == null) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("该用户不存在！").build();
		}
		BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
		if (!encoder.matches(user.getOldPassword(), chkUser.getPassword())) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("原密码错误！").build();
		}
		user.setPassword(encoder.encode(user.getPassword().trim()));
		if (userService.modifyUser(user) > 0) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("修改成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("修改失败").build();
		}
	}

	@RequestMapping("/batchDel")
	@ResponseBody
	public ApiResult batchDel(@RequestBody ApiParams<List<User>> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		if (params.getData().size() == userService.batchDel(params.getData())) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("删除成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("删除失败").build();
		}
	}

	@RequestMapping("/getRoles")
	@ResponseBody
	public ApiResult getRoles(@RequestParam(required = false) Long page, @RequestParam(required = false) Long limit) {
		if (page == null || page.longValue() == 0L || limit == null || limit.longValue() == 0L) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		User user = new User();
		user.setPage(page);
		user.setLimit(limit);
		Long count = userService.getRolesCount(user);
		ApiPagination pagination = new ApiPagination(page, limit, count);
		user.setOffset(pagination.getOffset());
		List<User> users = userService.getRoles(user);
		return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(users).pagination(pagination).build();
	}

	@RequestMapping("/disRole")
	@ResponseBody
	public ApiResult disRole(@RequestBody ApiParams<User> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		if (userService.disRole(params.getData()) > 0) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("分配成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("分配失败").build();
		}
	}
}
