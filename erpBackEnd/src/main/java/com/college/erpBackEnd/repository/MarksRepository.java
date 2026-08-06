package com.college.erpBackEnd.repository;

import com.college.erpBackEnd.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Long> {
    List<Marks> findByEnrollment_Id(Long enrollmentId);
    List<Marks> findByEnrollment_Course_Id(Long courseId);
}