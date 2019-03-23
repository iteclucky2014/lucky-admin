package com.lucky.admin.platform.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
//	int deleteByPrimaryKey(String id);
//
//	int insert(User record);
//
//	int insertSelective(User record);

	String selectByPrimaryKey(String id);

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