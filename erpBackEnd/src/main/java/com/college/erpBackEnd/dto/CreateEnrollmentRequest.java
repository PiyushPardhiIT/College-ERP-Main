package com.college.erpBackEnd.dto;

public class CreateEnrollmentRequest {
    private Long courseId;

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
}