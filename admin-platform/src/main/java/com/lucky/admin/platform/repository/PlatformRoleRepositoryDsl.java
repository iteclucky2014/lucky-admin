package com.lucky.admin.platform.repository;

import com.lucky.admin.platform.domain.PlatformRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PlatformRoleRepositoryDsl extends JpaRepository<PlatformRole, Long>, QuerydslPredicateExecutor<PlatformRole> {
}
