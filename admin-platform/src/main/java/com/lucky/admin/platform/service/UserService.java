package com.lucky.admin.platform.service;

import com.lucky.admin.platform.dao.UserMapper;
import com.lucky.admin.platform.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public User getUserByUsername(String username) {
		return userMapper.getUserByUsername(username);
	}
	
	@Transactional
	public int createUser(User user) {
		BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword().trim()));
		return userMapper.createUser(user);
	}

	@Transactional
	public int modifyUser(User user) {
		return userMapper.modifyUser(user);
	}
}
