package com.college.erpBackEnd.controller;

import com.college.erpBackEnd.dto.MarkAttendanceRequest;
import com.college.erpBackEnd.entity.Attendance;
import com.college.erpBackEnd.service.AttendanceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public Attendance markAttendance(@RequestBody MarkAttendanceRequest request) {
        return attendanceService.markAttendance(request.getEnrollmentId(), request.getDate(), request.getStatus());
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public List<Attendance> getAttendanceForCourse(@PathVariable Long courseId) {
        return attendanceService.getAttendanceForCourse(courseId);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public List<Attendance> getMyAttendance() {
        return attendanceService.getMyAttendance();
    }
}