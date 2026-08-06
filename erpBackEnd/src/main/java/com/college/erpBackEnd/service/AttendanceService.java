package com.college.erpBackEnd.service;

import com.college.erpBackEnd.entity.*;
import com.college.erpBackEnd.repository.*;
import com.college.erpBackEnd.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, EnrollmentRepository enrollmentRepository,
                             UserRepository userRepository, StudentProfileRepository studentProfileRepository) {
        this.attendanceRepository = attendanceRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.studentProfileRepository = studentProfileRepository;
    }

    public Attendance markAttendance(Long enrollmentId, java.time.LocalDate date, String statusStr) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found: " + enrollmentId));

        User currentUser = userRepository.findByUsername(SecurityUtils.getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Attendance attendance = new Attendance();
        attendance.setEnrollment(enrollment);
        attendance.setDate(date);
        attendance.setStatus(AttendanceStatus.valueOf(statusStr));
        attendance.setMarkedBy(currentUser);
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceForCourse(Long courseId) {
        return attendanceRepository.findByEnrollment_Course_Id(courseId); // admin/faculty, enforced at controller
    }

    public List<Attendance> getMyAttendance() {
        StudentProfile studentProfile = getCurrentStudentProfile();
        List<Enrollment> myEnrollments = enrollmentRepository.findByStudentProfile_Id(studentProfile.getId());

        List<Attendance> result = new java.util.ArrayList<>();
        for (Enrollment enrollment : myEnrollments) {
            result.addAll(attendanceRepository.findByEnrollment_Id(enrollment.getId()));
        }
        return result;
    }

    private StudentProfile getCurrentStudentProfile() {
        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return studentProfileRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("No student profile found for this account"));
    }
}