package com.college.erpBackEnd.repository;

import com.college.erpBackEnd.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEnrollment_Id(Long enrollmentId);
    List<Attendance> findByEnrollment_Course_Id(Long courseId);
}