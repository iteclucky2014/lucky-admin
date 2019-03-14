package com.lucky.admin.platform.security;

import com.querydsl.jpa.impl.JPAQueryFactory;

import com.lucky.admin.platform.domain.PlatformUser;
import com.lucky.admin.platform.domain.QPlatformUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

public class XXXUserDetailsService implements UserDetailsService {

    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QPlatformUser qPlatformUser = QPlatformUser.platformUser;
        PlatformUser platformUser = queryFactory.selectFrom(qPlatformUser)
                .where(qPlatformUser.username.eq(username)).fetchFirst();

        return platformUser;
    }

}
