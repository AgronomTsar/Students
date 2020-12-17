package com.students.service;

import com.j256.ormlite.dao.Dao;
import com.students.models.Student;

public class StudentRequest {
    private final Dao<Student, Integer> dao;

    public StudentRequest(Dao<Student, Integer> dao) {
        this.dao = dao;
    }
}
