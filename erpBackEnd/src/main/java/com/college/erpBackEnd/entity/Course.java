package com.college.erpBackEnd.entity;

import jakarta.persistence.*;

@Entity
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String name;
    private int credits;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;
}
