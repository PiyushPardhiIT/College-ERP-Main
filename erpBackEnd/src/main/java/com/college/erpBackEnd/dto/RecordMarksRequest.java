package com.college.erpBackEnd.dto;

public class RecordMarksRequest {
    private Long enrollmentId;
    private String examType; // "MIDTERM", "FINAL", "ASSIGNMENT", "QUIZ"
    private double score;
    private double maxScore;

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }
    public String getExamType() { return examType; }
    public void setExamType(String examType) { this.examType = examType; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public double getMaxScore() { return maxScore; }
    public void setMaxScore(double maxScore) { this.maxScore = maxScore; }
}