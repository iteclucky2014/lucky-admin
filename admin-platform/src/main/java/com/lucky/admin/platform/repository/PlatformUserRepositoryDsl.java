package com.lucky.admin.platform.repository;

import com.lucky.admin.platform.domain.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PlatformUserRepositoryDsl extends JpaRepository<PlatformUser, Long>, QuerydslPredicateExecutor<PlatformUser> {
}
