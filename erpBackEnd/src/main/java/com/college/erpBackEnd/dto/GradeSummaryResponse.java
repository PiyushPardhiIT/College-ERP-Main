package com.college.erpBackEnd.dto;

import java.util.List;

public class GradeSummaryResponse {
    private Long courseId;
    private String courseName;
    private double totalScore;
    private double totalMaxScore;
    private double percentage;
    private List<MarksDetail> details;

    public static class MarksDetail {
        private String examType;
        private double score;
        private double maxScore;

        public MarksDetail(String examType, double score, double maxScore) {
            this.examType = examType;
            this.score = score;
            this.maxScore = maxScore;
        }
        public String getExamType() { return examType; }
        public double getScore() { return score; }
        public double getMaxScore() { return maxScore; }
    }

    public GradeSummaryResponse(Long courseId, String courseName, double totalScore,
                                double totalMaxScore, double percentage, List<MarksDetail> details) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.totalScore = totalScore;
        this.totalMaxScore = totalMaxScore;
        this.percentage = percentage;
        this.details = details;
    }

    public Long getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public double getTotalScore() { return totalScore; }
    public double getTotalMaxScore() { return totalMaxScore; }
    public double getPercentage() { return percentage; }
    public List<MarksDetail> getDetails() { return details; }
}