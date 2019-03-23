package com.lucky.admin.platform.service;

import com.lucky.admin.platform.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public String getUserById(String id) {
		return userMapper.selectByPrimaryKey(id);
	}

//	@Override
//	public List<User> getAll() {
//		return userMapper.getAll();
//	}
//
//	@Override
//	public List<User> getAll2() {
//		return userMapper.getAll2();
//	}
//
//	@Override
//	public List<User> getAll3() {
//		return userMapper.getAll3();
//	}

}
