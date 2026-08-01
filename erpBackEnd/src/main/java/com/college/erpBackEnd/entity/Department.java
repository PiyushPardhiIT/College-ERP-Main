package com.college.erpBackEnd.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique=true, nullable = false)
    private String code;
}
