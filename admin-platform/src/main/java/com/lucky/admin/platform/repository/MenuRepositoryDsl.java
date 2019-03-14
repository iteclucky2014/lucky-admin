package com.lucky.admin.platform.repository;

import com.lucky.admin.platform.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MenuRepositoryDsl extends JpaRepository<Menu, Long>,QuerydslPredicateExecutor<Menu> {
}