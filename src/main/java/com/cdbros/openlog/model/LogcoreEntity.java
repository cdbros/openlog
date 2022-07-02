package com.cdbros.openlog.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "log")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogcoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "project_id")
    Long projectId;

    @Column(name = "hostname")
    String hostname;

    @Column(name = "date")
    Timestamp date;

    @Column(name = "severity")
    String severity;

    @Column(name = "code")
    String code;

    @Column(name = "action")
    String action;

    @Column(name = "message")
    String message;

}
