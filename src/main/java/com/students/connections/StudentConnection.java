package com.students.connections;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.students.deserializer.StudentDeserializer;
import com.students.models.Student;
import com.students.models.Student_group;
import com.students.service.GroupRequest;
import com.students.service.StudentRequest;
import io.javalin.Javalin;

import java.sql.SQLException;

public class StudentConnection {
    final String path="jdbc:sqlite:C:\\Users\\77012\\Desktop\\бд\\students.db";
    Dao<Student,Integer> StudentDao;
    Dao<Student_group,Integer> GroupDao;
    StudentRequest st;
    GroupRequest g;
    Javalin app;
    String student="/student";
    String studentId="/student/:id";
    String studentPatch="/studentPatch";
    String studentSave="/studentSave";

    public StudentConnection(Dao<Student, Integer> studentDao, Dao<Student_group, Integer> groupDao, StudentRequest st, GroupRequest g,Javalin app) {
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
        app.delete("/student/:id",ctx->{
            st.deleteStudent(ctx,Integer.parseInt(ctx.pathParam("id")));
            ctx.status(204).result("204");
        });
    }


}
