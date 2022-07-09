package com.cdbros.openlog.feature.logcore.repository;

import com.cdbros.openlog.model.LogcoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface LogcoreRepository extends JpaRepository<LogcoreEntity, Long>, JpaSpecificationExecutor<LogcoreEntity> {

    Page<LogcoreEntity> findAll(Pageable pageable);
}
