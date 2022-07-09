package com.cdbros.openlog.feature.logcore.controller.dto;

import com.cdbros.openlog.util.Utils;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginatedLogRequest {

    @Builder.Default
    int page = 0;

    @Builder.Default
    int size = 20;

    String orderBy;

    @Builder.Default
    boolean ascOrder = false;

    String severity;

    @NotNull
    Long projectId;

    @DateTimeFormat(pattern = Utils.DATE_PATTERN)
    String from;

    @DateTimeFormat(pattern = Utils.DATE_PATTERN)
    String to;
}
