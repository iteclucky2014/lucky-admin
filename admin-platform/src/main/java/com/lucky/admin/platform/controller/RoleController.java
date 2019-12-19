package com.lucky.admin.platform.controller;

import com.lucky.admin.platform.common.*;
import com.lucky.admin.platform.service.RoleService;
import com.lucky.admin.platform.vo.Role;
import com.lucky.admin.platform.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping("/getRoles")
	@ResponseBody
	public ApiResult getRoleByCondition(@RequestParam(required = false) Long page, @RequestParam(required = false) Long limit,
										@RequestParam(required = false) String roleName, @RequestParam(required = false) String roleDesc,
										@RequestParam(required = false) String isDelete) {
		if (page == null || page.longValue() == 0L || limit == null || limit.longValue() == 0L) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		Role role = new Role();
		role.setPage(page);
		role.setLimit(limit);
		role.setRoleName(roleName);
		role.setRoleDesc(roleDesc);
		role.setIsDelete(isDelete);
		Long count = roleService.getRolesCount(role);
		ApiPagination pagination = new ApiPagination(page, limit, count);
		role.setOffset(pagination.getOffset());
		List<Role> roles = roleService.getRoles(role);
		return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(roles).pagination(pagination).build();
	}

	@RequestMapping("/create")
	@ResponseBody
	public ApiResult createRole(@RequestBody ApiParams<Role> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		Role role = params.getData();
		if (roleService.getRoleByName(role.getRoleName()) != null) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("该角色已存在！").build();
		}
		if (roleService.createRole(role) > 0) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("添加成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("添加失败").build();
		}
	}

	@RequestMapping("/modify")
	@ResponseBody
	public ApiResult modifyRole(@RequestBody ApiParams<Role> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		Role role = params.getData();
		if (roleService.chkRole(role) != null) {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("该角色已存在！").build();
		}
		if (roleService.modifyRole(role) > 0) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("修改成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("修改失败").build();
		}
	}

	@RequestMapping("/batchDel")
	@ResponseBody
	public ApiResult batchDel(@RequestBody ApiParams<List<Role>> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		if (params.getData().size() == roleService.batchDel(params.getData())) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("删除成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("删除失败").build();
		}
	}

	@RequestMapping("/getRoleUser")
	@ResponseBody
	public ApiResult getRoleUser(@RequestParam(required = false) Long page, @RequestParam(required = false) Long limit, @RequestParam(required = false) Integer id) {
		if (page == null || page.longValue() == 0L || limit == null || limit.longValue() == 0L || id == null || id.intValue() == 0) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		Role role = new Role();
		role.setPage(page);
		role.setLimit(limit);
		role.setId(id);
		Long count = roleService.getRoleUserCount(role);
		ApiPagination pagination = new ApiPagination(page, limit, count);
		role.setOffset(pagination.getOffset());
		List<User> users = roleService.getRoleUser(role);
		return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(users).pagination(pagination).build();
	}

	@RequestMapping("/getRoleAuth")
	@ResponseBody
	public ApiResult getRoleAuth(@RequestParam(required = false) Integer id) {
		if (id == null || id.intValue() == 0) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		Role role = new Role();
		role.setId(id);
		List<Role> menus = roleService.getRoleAuth(role);
		return ApiResultBuilder.create().code(ApiResultCode.Success.code()).data(menus).build();
	}

	@RequestMapping("/disAuth")
	@ResponseBody
	public ApiResult disAuth(@RequestBody ApiParams<List<Role>> params) {
		if (params.getData() == null) {
			return ApiResultBuilder.create().code(ApiResultCode.DataIllegality.code()).msg(ApiResultCode.DataIllegality.msg()).build();
		}
		if (roleService.disAuth(params.getData()) > 0) {
			return ApiResultBuilder.create().code(ApiResultCode.Success.code()).msg("分配成功").build();
		} else {
			return ApiResultBuilder.create().code(ApiResultCode.BusinessException.code()).msg("分配失败").build();
		}
	}
}
