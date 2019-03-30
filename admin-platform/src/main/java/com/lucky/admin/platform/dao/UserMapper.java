package com.lucky.admin.platform.dao;

import com.lucky.admin.platform.vo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

	User getUserByUsername(String username);
	
//	int deleteByPrimaryKey(String id);
//
//	int insert(User record);
//
//	int insertSelective(User record);
//
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