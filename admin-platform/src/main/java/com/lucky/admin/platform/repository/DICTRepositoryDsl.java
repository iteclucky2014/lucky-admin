package com.lucky.admin.platform.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.lucky.admin.platform.domain.DICT;

public interface DICTRepositoryDsl extends JpaRepository<DICT, Long>,QuerydslPredicateExecutor<DICT> {
}