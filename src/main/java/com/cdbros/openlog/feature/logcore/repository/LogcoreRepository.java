package com.cdbros.openlog.feature.logcore.repository;

import com.cdbros.openlog.model.LogcoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogcoreRepository extends JpaRepository<LogcoreEntity, Long> {
}
