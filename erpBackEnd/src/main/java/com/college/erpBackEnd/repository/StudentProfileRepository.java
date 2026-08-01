package com.college.erpBackEnd.repository;

import com.college.erpBackEnd.entity.StudentProfile;
import org.hibernate.internal.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    Optional<StudentProfile> findByUser_Id(Long userId);
}