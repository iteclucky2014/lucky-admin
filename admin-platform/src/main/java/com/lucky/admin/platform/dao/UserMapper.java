package com.lucky.admin.platform.dao;

import com.lucky.admin.platform.vo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

	User getUserByUsername(String username);
	
	List<User> getUserByCondition(User user);

	Long getUserCountByCondition(User user);

	int createUser(User user);

	int modifyUser(User user);
	
	int batchDel(@Param(value = "list") List<User> list);

	List<User> getRoles(User user);

	Long getRolesCount(User user);

	int disRole(User user);
}