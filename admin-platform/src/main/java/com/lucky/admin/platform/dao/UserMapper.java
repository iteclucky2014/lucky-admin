package com.lucky.admin.platform.dao;

import com.lucky.admin.platform.vo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

	User getUserByUsername(String username);

	int createUser(User user);

	int modifyUser(User user);
}