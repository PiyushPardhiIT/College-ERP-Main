package com.college.erpBackEnd.dto;

public class CreateStudentProfileRequest {
    private Long userId;
    private String rollNumber;
    private Long departmentId;
    private int semester;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }
}