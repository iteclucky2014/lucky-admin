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

	@RequestMapping("/getMenus")
	@ResponseBody
	public ApiResult getMenuByCondition(@RequestParam(required = false) String isDelete) {
		Menu menu = new Menu();
		menu.setIsDelete(isDelete);
		List<Menu> menus = menuService.getMenus(menu);
		return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(menus).build();
	}

	@RequestMapping("/create")
	@ResponseBody
	public ApiResult createMenu(@RequestBody ApiParams<Menu> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		Menu menu = params.getData();
		List<Menu> menus = menuService.chkRepeatMenu(menu);
		if (menus != null && !menus.isEmpty()) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("相同目录下的菜单顺序或地址不能重复！").build();
		}
		if (menuService.createMenu(menu) > 0) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("添加成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("添加失败").build();
		}
	}

	@RequestMapping("/modify")
	@ResponseBody
	public ApiResult modifyMenu(@RequestBody ApiParams<Menu> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		Menu menu = params.getData();
		List<Menu> menus = menuService.chkMenu(menu);
		if (menus != null && !menus.isEmpty()) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("相同目录下的菜单顺序或地址不能重复！").build();
		}
		if (menuService.modifyMenu(menu) > 0) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("修改成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("修改失败").build();
		}
	}

	@RequestMapping("/batchDel")
	@ResponseBody
	public ApiResult batchDel(@RequestBody ApiParams<List<Menu>> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		List<Menu> menus = params.getData();
		for (Menu menu : menus) {
			Menu m = new Menu();
			m.setRoot(menu.getId());
			if (menuService.getSubMenuCnt(m) > 0) {
				return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("被删除的菜单不能拥有子目录！").build();
			}
		}
		if (params.getData().size() == menuService.batchDel(params.getData())) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("删除成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("删除失败").build();
		}
	}
}
