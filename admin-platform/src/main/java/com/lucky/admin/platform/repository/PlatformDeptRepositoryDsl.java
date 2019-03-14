package com.lucky.admin.platform.repository;

import com.lucky.admin.platform.domain.PlatformDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PlatformDeptRepositoryDsl extends JpaRepository<PlatformDept, Long>, QuerydslPredicateExecutor<PlatformDept> {
}
