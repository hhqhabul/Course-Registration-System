package com.acme.regsys.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "courses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false)
    private String code;   // e.g., COMP248

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int credits;

    @Column(nullable = false)
    private int dayOfWeek; // 1=Mon ... 7=Sun

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;
}
