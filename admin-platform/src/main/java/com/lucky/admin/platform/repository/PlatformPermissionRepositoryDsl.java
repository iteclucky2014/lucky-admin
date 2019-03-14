package com.lucky.admin.platform.repository;

import com.lucky.admin.platform.domain.PlatformPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PlatformPermissionRepositoryDsl extends JpaRepository<PlatformPermission, Long>, QuerydslPredicateExecutor<PlatformPermission> {
}
