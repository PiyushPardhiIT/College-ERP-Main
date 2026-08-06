package com.college.erpBackEnd.controller;

import com.college.erpBackEnd.dto.CreateFacultyProfileRequest;
import com.college.erpBackEnd.dto.CreateStudentProfileRequest;
import com.college.erpBackEnd.entity.FacultyProfile;
import com.college.erpBackEnd.entity.StudentProfile;
import com.college.erpBackEnd.service.ProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@PreAuthorize("hasRole('ADMIN')") // admin creates profiles after registering the base user account
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/students")
    public StudentProfile createStudentProfile(@RequestBody CreateStudentProfileRequest request) {
        return profileService.createStudentProfile(request);
    }

    @PostMapping("/faculty")
    public FacultyProfile createFacultyProfile(@RequestBody CreateFacultyProfileRequest request) {
        return profileService.createFacultyProfile(request);
    }
}