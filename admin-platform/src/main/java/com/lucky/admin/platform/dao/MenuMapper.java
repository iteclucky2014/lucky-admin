package com.lucky.admin.platform.dao;

import com.lucky.admin.platform.vo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {

	List<Menu> getAuthMenu(Menu menu);
}