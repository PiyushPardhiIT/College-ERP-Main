package com.college.erpBackEnd.controller;

import com.college.erpBackEnd.dto.GradeSummaryResponse;
import com.college.erpBackEnd.dto.RecordMarksRequest;
import com.college.erpBackEnd.entity.Marks;
import com.college.erpBackEnd.service.MarksService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
public class MarksController {

    private final MarksService marksService;

    public MarksController(MarksService marksService) {
        this.marksService = marksService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public Marks recordMarks(@RequestBody RecordMarksRequest request) {
        return marksService.recordMarks(request.getEnrollmentId(), request.getExamType(),
                request.getScore(), request.getMaxScore());
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public List<Marks> getMarksForCourse(@PathVariable Long courseId) {
        return marksService.getMarksForCourse(courseId);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public List<GradeSummaryResponse> getMyGrades() {
        return marksService.getMyGrades();
    }
}