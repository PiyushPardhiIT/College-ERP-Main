package com.college.erpBackEnd.service;

import com.college.erpBackEnd.entity.Course;
import com.college.erpBackEnd.entity.Enrollment;
import com.college.erpBackEnd.entity.StudentProfile;
import com.college.erpBackEnd.entity.User;
import com.college.erpBackEnd.repository.CourseRepository;
import com.college.erpBackEnd.repository.EnrollmentRepository;
import com.college.erpBackEnd.repository.StudentProfileRepository;
import com.college.erpBackEnd.repository.UserRepository;
import com.college.erpBackEnd.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository,
                             StudentProfileRepository studentProfileRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
    }

    public Enrollment enrollSelf(Long courseId) {
        StudentProfile studentProfile = getCurrentStudentProfile();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudentProfile(studentProfile);
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getMyEnrollments() {
        StudentProfile studentProfile = getCurrentStudentProfile();
        return enrollmentRepository.findByStudentProfile_Id(studentProfile.getId());
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll(); // admin/faculty only, enforced at controller level
    }

    private StudentProfile getCurrentStudentProfile() {
        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return studentProfileRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("No student profile found for this account"));
    }
}