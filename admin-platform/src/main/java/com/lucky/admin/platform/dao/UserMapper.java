package com.lucky.admin.platform.dao;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserMapper {
	
//	int deleteByPrimaryKey(String id);
//
//	int insert(User record);
//
//	int insertSelective(User record);

	Map getUserByUsername(String username);

//	int updateByPrimaryKeySelective(User record);
//
//	int updateByPrimaryKey(User record);
//
//	List<User> getAll();
//
//	List<User> getAll2();
//
//	List<User> getAll3();
}