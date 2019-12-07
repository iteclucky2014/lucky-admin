package com.lucky.admin.platform.controller;

import com.lucky.admin.platform.common.*;
import com.lucky.admin.platform.service.MenuService;
import com.lucky.admin.platform.vo.Menu;
import com.lucky.admin.platform.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@RequestMapping("/getAuthMenu")
	@ResponseBody
	public ApiResult getAuthMenu() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User) principal;
		Menu menu = new Menu();
		menu.setUserId(user.getId());
		menu.setRoot(0);
		List<Menu> menus = menuService.getAuthMenu(menu);
		return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(menus).build();
	}
}
