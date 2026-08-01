package com.college.erpBackEnd.repository;

import com.college.erpBackEnd.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {}