package com.college.erpBackEnd.repository;

import com.college.erpBackEnd.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {}