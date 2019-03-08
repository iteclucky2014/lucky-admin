package cn.sambo.difference.platform.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import cn.sambo.difference.platform.domain.DICT;

public interface DICTRepositoryDsl extends JpaRepository<DICT, Long>,QuerydslPredicateExecutor<DICT> {
}