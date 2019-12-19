package com.lucky.admin.platform.dao;

import com.lucky.admin.platform.vo.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper {

	List<Menu> getAuthMenu(Menu menu);

	List<Menu> getMenus(Menu menu);

	int createMenu(Menu menu);

	int addSuperAdminAuth(Menu menu);

	List<Menu> chkRepeatMenu(Menu menu);

	int modifyMenu(Menu menu);

	List<Menu> chkMenu(Menu menu);
	
	int getSubMenuCnt(Menu menu);

	int batchDel(@Param(value = "list") List<Menu> list);

	int delAuth(@Param(value = "list") List<Menu> list);
}