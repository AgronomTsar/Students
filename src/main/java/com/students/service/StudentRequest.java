package com.students.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.j256.ormlite.dao.Dao;
import com.students.serializers.StudentSerializer;
import com.students.models.Student;
import com.students.models.StudentGroup;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRequest {
    private final Dao<Student, Integer> dao;
    public StudentRequest(Dao<Student, Integer> dao) {
        this.dao = dao;
    }
    public List<String> findAllStudents(Context ctx) throws SQLException, JsonProcessingException {
        ArrayList<Student> students= (ArrayList<Student>) dao.queryForAll();
        ObjectMapper om=new ObjectMapper();
        SimpleModule m=new SimpleModule();
        m.addSerializer(Student.class,new StudentSerializer());
        om.registerModule(m);
        ArrayList<String> string=new ArrayList<>();
        int count=0;
        while (count<students.size()){
            string.add(om.writeValueAsString(students.get(count)));
            count++;
        }
        ctx.status(200);
        return string;

    }
    public String findStudentById(int id) throws SQLException, JsonProcessingException {
        Student student=dao.queryForId(id);
        ObjectMapper om=new ObjectMapper();
        SimpleModule m=new SimpleModule();
        m.addSerializer(Student.class,new StudentSerializer());
        om.registerModule(m);
        return om.writeValueAsString(student);
    }
    public String updateStudentById(Context ctx,int id, String first_name, String last_name, String phone, String email, StudentGroup id_group) throws SQLException, JsonProcessingException {
        Student student=dao.queryForId(id);
        student.setFirst_name(first_name);
        student.setLast_name(last_name);
        student.setPhone(phone);
        student.setEmail(email);
        student.setId_group(id_group);
        dao.update(student);
        ctx.status(200);
        return findStudentById(id) ;
    }
    public String saveStudent(Context ctx, int id, String firstName, String lastName, String phone, String email, StudentGroup id_group) throws SQLException, JsonProcessingException {
        Student student=new Student(id,firstName,lastName,phone,email,id_group);
        dao.create(student);
        ctx.status(201);
        return findStudentById(id);
    }
    public void deleteStudent(Context ctx,int id) throws SQLException {
        dao.deleteById(id);
        ctx.status(204);
    }
}


