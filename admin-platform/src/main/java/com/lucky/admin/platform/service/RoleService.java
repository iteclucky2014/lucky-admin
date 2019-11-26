package com.lucky.admin.platform.service;

import com.lucky.admin.platform.dao.RoleMapper;
import com.lucky.admin.platform.vo.Role;
import com.lucky.admin.platform.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	public List<Role> getRoles(Role role) {
		return roleMapper.getRoles(role);
	}

	public Long getRolesCount(Role role) {
		return roleMapper.getRolesCount(role);
	}

	public Role chkRole(Role role) {
		return roleMapper.chkRole(role);
	}

	public Role getRoleByName(String roleName) {
		return roleMapper.getRoleByName(roleName);
	}

	@Transactional
	public int createRole(Role role) {
		return roleMapper.createRole(role);
	}

	@Transactional
	public int modifyRole(Role role) {
		return roleMapper.modifyRole(role);
	}

	@Transactional
	public int batchDel(List<Role> list) {
		int count = roleMapper.batchDel(list);
		roleMapper.delAuth(list);
		roleMapper.updUser(list);
		return count;
	}

	public List<User> getRoleUser(Role role) {
		List<Map<String, Object>> maps = roleMapper.getRoleUser(role);
		List<User> users = new ArrayList<>();
		for (Map<String, Object> map : maps) {
			User user = new User();
			user.setId(Integer.parseInt(map.get("ID").toString()));
			user.setUsername(map.get("USERNAME") != null ? map.get("USERNAME").toString() : "");
			user.setNickname(map.get("NICKNAME") != null ? map.get("NICKNAME").toString() : "");
			user.setSex(map.get("SEX") != null ? map.get("SEX").toString() : "");
			user.setMobile(map.get("MOBILE") != null ? map.get("MOBILE").toString() : "");
			user.setEmail(map.get("EMAIL") != null ? map.get("EMAIL").toString() : "");
			users.add(user);
		}
		return users;
	}

	public Long getRoleUserCount(Role role) {
		return roleMapper.getRoleUserCount(role);
	}
	
	public List<Role> getRoleAuth(Role role) {
		return roleMapper.getRoleAuth(role);
	}

	@Transactional
	public int disAuth(List<Role> list) {
		roleMapper.delAuth(list);
		return roleMapper.addAuth(list);
	}
}
