package com.lucky.admin.platform.dao;

import com.lucky.admin.platform.vo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleMapper {

	List<Role> getRoles(Role role);

	Long getRolesCount(Role role);

	Role chkRole(Role role);

	Role getRoleByName(String roleName);

	int createRole(Role role);

	int modifyRole(Role role);

	int batchDel(@Param(value = "list") List<Role> list);

	int delAuth(@Param(value = "list") List<Role> list);

	int updUser(@Param(value = "list") List<Role> list);

	List<Map<String, Object>> getRoleUser(Role role);

	Long getRoleUserCount(Role role);
	
	List<Role> getRoleAuth(Role role);

	int addAuth(@Param(value = "list") List<Role> list);
}