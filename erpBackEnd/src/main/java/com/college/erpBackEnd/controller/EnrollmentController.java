package com.college.erpBackEnd.controller;

import com.college.erpBackEnd.dto.CreateEnrollmentRequest;
import com.college.erpBackEnd.entity.Enrollment;
import com.college.erpBackEnd.service.EnrollmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public Enrollment enroll(@RequestBody CreateEnrollmentRequest request) {
        return enrollmentService.enrollSelf(request.getCourseId());
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public List<Enrollment> getMyEnrollments() {
        return enrollmentService.getMyEnrollments();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public List<Enrollment> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }
}