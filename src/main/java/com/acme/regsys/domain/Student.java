package com.acme.regsys.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String passwordHash;

  @Column(nullable = false)
  @Builder.Default
  private java.time.Instant createdAt = java.time.Instant.now(); // ensure builder uses this default

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private java.util.List<Enrollment> enrollments = new java.util.ArrayList<>();

  @PrePersist
  void prePersist() {                    // double safety
    if (createdAt == null) createdAt = java.time.Instant.now();
  }
}

