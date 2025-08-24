package com.acme.regsys.repo;

import com.acme.regsys.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, String> {
    Optional<Student> findByEmail(String email);
}
