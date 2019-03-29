package com.lucky.admin.platform.security;

import com.lucky.admin.platform.dao.UserMapper;
import com.lucky.admin.platform.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.Map;

public class LoginService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User();

        Map userMap = userMapper.getUserByUsername(username);
        user.setUsername((String)userMap.get("USERNAME"));
        user.setPassword((String)userMap.get("PASSWORD"));

        return user;
    }
}
