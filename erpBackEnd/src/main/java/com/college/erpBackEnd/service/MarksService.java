package com.college.erpBackEnd.service;

import com.college.erpBackEnd.dto.GradeSummaryResponse;
import com.college.erpBackEnd.entity.*;
import com.college.erpBackEnd.repository.*;
import com.college.erpBackEnd.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarksService {

    private final MarksRepository marksRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;

    public MarksService(MarksRepository marksRepository, EnrollmentRepository enrollmentRepository,
                        UserRepository userRepository, StudentProfileRepository studentProfileRepository) {
        this.marksRepository = marksRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.studentProfileRepository = studentProfileRepository;
    }

    public Marks recordMarks(Long enrollmentId, String examTypeStr, double score, double maxScore) {
        if (score > maxScore) {
            throw new RuntimeException("Score cannot exceed max score");
        }
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found: " + enrollmentId));

        Marks marks = new Marks();
        marks.setEnrollment(enrollment);
        marks.setExamType(ExamType.valueOf(examTypeStr));
        marks.setScore(score);
        marks.setMaxScore(maxScore);
        return marksRepository.save(marks);
    }

    public List<Marks> getMarksForCourse(Long courseId) {
        return marksRepository.findByEnrollment_Course_Id(courseId); // admin/faculty, enforced at controller
    }

    public List<GradeSummaryResponse> getMyGrades() {
        StudentProfile studentProfile = getCurrentStudentProfile();
        List<Enrollment> myEnrollments = enrollmentRepository.findByStudentProfile_Id(studentProfile.getId());

        return myEnrollments.stream()
                .map(this::buildSummaryForEnrollment)
                .collect(Collectors.toList());
    }

    private GradeSummaryResponse buildSummaryForEnrollment(Enrollment enrollment) {
        List<Marks> marksList = marksRepository.findByEnrollment_Id(enrollment.getId());

        double totalScore = marksList.stream().mapToDouble(Marks::getScore).sum();
        double totalMaxScore = marksList.stream().mapToDouble(Marks::getMaxScore).sum();
        double percentage = totalMaxScore > 0 ? (totalScore / totalMaxScore) * 100 : 0;

        List<GradeSummaryResponse.MarksDetail> details = marksList.stream()
                .map(m -> new GradeSummaryResponse.MarksDetail(m.getExamType().name(), m.getScore(), m.getMaxScore()))
                .collect(Collectors.toList());

        Course course = enrollment.getCourse();
        return new GradeSummaryResponse(course.getId(), course.getName(), totalScore, totalMaxScore, percentage, details);
    }

    private StudentProfile getCurrentStudentProfile() {
        String username = SecurityUtils.getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return studentProfileRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new RuntimeException("No student profile found for this account"));
    }
}