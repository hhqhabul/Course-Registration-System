package com.acme.regsys.config;

import com.acme.regsys.domain.Course;
import com.acme.regsys.domain.Student;
import com.acme.regsys.repo.CourseRepo;
import com.acme.regsys.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

  @Bean
  CommandLineRunner seed(StudentRepo students, CourseRepo courses) {
    return args -> {
      if (students.count() == 0) {
        students.save(Student.builder()
          .name("Demo Student")
          .email("demo@uni.ca")
          .passwordHash("{noop}demo") // temporary; real auth later
          .build());
      }

      if (courses.count() == 0) {
        courses.save(Course.builder()
          .code("COMP248").title("OOP I").capacity(3).credits(3)
          .dayOfWeek(1).startTime(LocalTime.of(9,0)).endTime(LocalTime.of(10,30))
          .build());
        courses.save(Course.builder()
          .code("COMP352").title("Data Structures").capacity(2).credits(3)
          .dayOfWeek(1).startTime(LocalTime.of(10,30)).endTime(LocalTime.of(12,0))
          .build());
        courses.save(Course.builder()
          .code("SOEN287").title("Web Programming").capacity(1).credits(3)
          .dayOfWeek(3).startTime(LocalTime.of(14,0)).endTime(LocalTime.of(15,30))
          .build());
      }
    };
  }
}
