package com.students.controllers;

import com.j256.ormlite.dao.Dao;
import com.students.deserializer.StudentDeserializer;
import com.students.models.Student;
import com.students.models.studentGroup;
import com.students.service.GroupRequest;
import com.students.service.StudentRequest;
import io.javalin.Javalin;

public class StudentControllers {
    Dao<Student,Integer> StudentDao;
    Dao<studentGroup,Integer> GroupDao;
    StudentRequest st;
    GroupRequest g;
    Javalin app;
    String student="/student";
    String studentId="/student/:id";
    String studentPatch="/studentPatch";
    String studentSave="/studentSave";
    String studentDelete="/studentDelete";

    public StudentControllers(Dao<Student, Integer> studentDao, Dao<studentGroup, Integer> groupDao, StudentRequest st, GroupRequest g, Javalin app) {
        StudentDao = studentDao;
        GroupDao = groupDao;
        this.st = st;
        this.g = g;
        this.app=app;
    }
    public void getStudent(){
        app.get(student,ctx->ctx.result(String.valueOf(st.findAllStudents(ctx))));
    }
    public void getStudentById(){
        app.get(studentId,ctx->{
            ctx.result(st.findStudentById(Integer.parseInt(ctx.pathParam("id"))));
            ctx.status(200);
        });
    }
    public void updateStudent(){
        app.patch(studentPatch,ctx->{
            String json=ctx.body();
            Student student= StudentDeserializer.studentObjectConverter(json,GroupDao);
            ctx.result(st.updateStudentById(ctx,student.getId(),student.getFirst_name(),student.getLast_name(),student.getPhone(),student.getEmail(),student.getId_group()));
        });
    }
    public void saveStudent(){
        app.post(studentSave,ctx->{
            String json=ctx.body();
            Student student=StudentDeserializer.studentObjectConverter(json,GroupDao);
            ctx.result(st.saveStudent(ctx,student.getId(),student.getFirst_name(),student.getLast_name(),student.getPhone(),
                    student.getEmail(),student.getId_group()));
        });
    }
    public void deleteStudent(){
        app.delete(studentDelete,ctx->{
            st.deleteStudent(ctx,Integer.parseInt(ctx.pathParam("id")));
            ctx.status(204).result("204");
        });
    }


}
