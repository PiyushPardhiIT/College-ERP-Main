package com.college.erpBackEnd.controller;

import com.college.erpBackEnd.entity.Course;
import com.college.erpBackEnd.entity.Department;
import com.college.erpBackEnd.repository.CourseRepository;
import com.college.erpBackEnd.repository.DepartmentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    public CourseController(CourseRepository courseRepository, DepartmentRepository departmentRepository) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public List<Course> getAll() {
        return courseRepository.findAll(); // any authenticated user can browse courses
    }

    @GetMapping("/{id}")
    public Course getOne(@PathVariable Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found: " + id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public Course create(@RequestBody Course course) {
        // course.department comes in as {"id": X} from client; verify it exists
        Department department = departmentRepository.findById(course.getDepartment().getId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        course.setDepartment(department);
        return courseRepository.save(course);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public Course update(@PathVariable Long id, @RequestBody Course updated) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found: " + id));
        course.setName(updated.getName());
        course.setCode(updated.getCode());
        course.setCredits(updated.getCredits());
        return courseRepository.save(course);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        courseRepository.deleteById(id);
    }
}