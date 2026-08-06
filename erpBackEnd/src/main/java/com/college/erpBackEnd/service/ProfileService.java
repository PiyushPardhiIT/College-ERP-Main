package com.college.erpBackEnd.service;

import com.college.erpBackEnd.dto.CreateFacultyProfileRequest;
import com.college.erpBackEnd.dto.CreateStudentProfileRequest;
import com.college.erpBackEnd.entity.*;
import com.college.erpBackEnd.repository.*;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final FacultyProfileRepository facultyProfileRepository;

    public ProfileService(UserRepository userRepository, DepartmentRepository departmentRepository,
                          StudentProfileRepository studentProfileRepository,
                          FacultyProfileRepository facultyProfileRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.facultyProfileRepository = facultyProfileRepository;
    }

    public StudentProfile createStudentProfile(CreateStudentProfileRequest request) {
        if (studentProfileRepository.findByUser_Id(request.getUserId()).isPresent()) {
            throw new RuntimeException("Student profile already exists for this user");
        }
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        StudentProfile profile = new StudentProfile();
        profile.setUser(user);
        profile.setRollNumber(request.getRollNumber());
        profile.setDepartment(department);
        profile.setSemester(request.getSemester());
        return studentProfileRepository.save(profile);
    }

    public FacultyProfile createFacultyProfile(CreateFacultyProfileRequest request) {
        if (facultyProfileRepository.findByUser_Id(request.getUserId()).isPresent()) {
            throw new RuntimeException("Faculty profile already exists for this user");
        }
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        FacultyProfile profile = new FacultyProfile();
        profile.setUser(user);
        profile.setDesignation(request.getDesignation());
        profile.setDepartment(department);
        return facultyProfileRepository.save(profile);
    }
}