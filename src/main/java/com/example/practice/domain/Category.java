package com.example.practice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "categories")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Course> courses = new ArrayList<>();

    @Builder
    public Category(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }
}
