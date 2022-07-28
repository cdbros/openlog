package com.cdbros.openlog.feature.logcore.repository;

import com.cdbros.openlog.feature.logcore.controller.dto.PaginatedLogRequest;
import com.cdbros.openlog.model.LogcoreEntity;
import com.cdbros.openlog.util.Utils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class LogcoreSpecification {

    public Specification<LogcoreEntity> filterLogs(PaginatedLogRequest logRequest) {
        return (Root<LogcoreEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(cb.lower(root.get("projectId")), logRequest.getProjectId()));

            if (logRequest.getSeverity() != null && !logRequest.getSeverity().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("severity")), logRequest.getSeverity().toLowerCase()));
            }

            if (logRequest.getFrom() != null && logRequest.getTo() != null) {
                predicates.add(cb.between(root.get("date"), Utils.getTimestampFromString(logRequest.getFrom()), Utils.getTimestampFromString(logRequest.getTo())));
            }
            else if (logRequest.getFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("date"), Utils.getTimestampFromString(logRequest.getFrom())));
            }
            else if (logRequest.getTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("date"), Utils.getTimestampFromString(logRequest.getTo())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<LogcoreEntity> getLastTwentyFourHoursErrors(Long projectId, String start) {
        return (Root<LogcoreEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(cb.lower(root.get("projectId")), projectId));
            predicates.add(cb.equal(cb.lower(root.get("severity")), "ERROR"));

            Timestamp startDate = Utils.getTimestampFromString(start);
            Timestamp endDate = Timestamp.from(startDate.toInstant().minus(1, ChronoUnit.DAYS));

            predicates.add(cb.between(root.get("date"), endDate, startDate));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
