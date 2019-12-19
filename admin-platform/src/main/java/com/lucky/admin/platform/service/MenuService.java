package com.lucky.admin.platform.service;

import com.lucky.admin.platform.dao.MenuMapper;
import com.lucky.admin.platform.vo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

	@Autowired
	private MenuMapper menuMapper;

	public List<Menu> getAuthMenu(Menu param) {
		List<Menu> menus = menuMapper.getAuthMenu(param);
		for (Menu menu : menus) {
			if (menu.getAddress() != null) {
				continue;
			}
			menu.setRoot(menu.getId());
			List<Menu> menus2 = menuMapper.getAuthMenu(menu);
			for (Menu menu2 : menus2) {
				if (menu2.getAddress() != null) {
					continue;
				}
				menu2.setRoot(menu2.getId());
				List<Menu> menus3 = menuMapper.getAuthMenu(menu2);
				menu2.setList(menus3);
			}
			menu.setList(menus2);
		}
		return menus;
	}
	
	public List<Menu> getMenus(Menu menu) {
		return menuMapper.getMenus(menu);
	}

	@Transactional
	public int createMenu(Menu menu) {
		int count = menuMapper.createMenu(menu);
		menuMapper.addSuperAdminAuth(menu);
		return count;
	}

	public List<Menu> chkRepeatMenu(Menu menu) {
		return menuMapper.chkRepeatMenu(menu);
	}

	@Transactional
	public int modifyMenu(Menu menu) {
		return menuMapper.modifyMenu(menu);
	}

	public List<Menu> chkMenu(Menu menu) {
		return menuMapper.chkMenu(menu);
	}
	
	public int getSubMenuCnt(Menu menu) {
		return menuMapper.getSubMenuCnt(menu);
	}

	@Transactional
	public int batchDel(List<Menu> list) {
		int count = menuMapper.batchDel(list);
		menuMapper.delAuth(list);
		return count;
	}
}
