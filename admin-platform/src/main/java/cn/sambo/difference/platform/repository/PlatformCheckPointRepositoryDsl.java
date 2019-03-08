package cn.sambo.difference.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import cn.sambo.difference.platform.domain.PlatformCheckPoint;

public interface PlatformCheckPointRepositoryDsl
        extends JpaRepository<PlatformCheckPoint, Long>, QuerydslPredicateExecutor<PlatformCheckPoint> {
}
