package com.college.erpBackEnd.dto;

import java.time.LocalDate;

public class MarkAttendanceRequest {
    private Long enrollmentId;
    private LocalDate date;
    private String status; // "PRESENT" or "ABSENT"

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}