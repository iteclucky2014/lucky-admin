package com.lucky.admin.platform.service;

import com.lucky.admin.platform.dao.UserMapper;
import com.lucky.admin.platform.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public User getUserByUsername(String username) {
		return userMapper.getUserByUsername(username);
	}
	
	public List<User> getUserByCondition(User user) {
		return userMapper.getUserByCondition(user);
	}
	
	public Long getUserCountByCondition(User user) {
		return userMapper.getUserCountByCondition(user);
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

	@Transactional
	public int batchDel(List<User> list) {
		return userMapper.batchDel(list);
	}

	public List<User> getRoles(User user) {
		return userMapper.getRoles(user);
	}

	public Long getRolesCount(User user) {
		return userMapper.getRolesCount(user);
	}

	@Transactional
	public int disRole(User user) {
		return userMapper.disRole(user);
	}
}
