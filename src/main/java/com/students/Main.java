package com.students;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.students.controllers.GroupControllers;
import com.students.controllers.StudentControllers;
import com.students.models.Student;
import com.students.models.StudentGroup;
import com.students.service.GroupRequest;
import com.students.service.StudentRequest;
import io.javalin.Javalin;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String path="jdbc:sqlite:C:\\Users\\77012\\Desktop\\бд\\students.db";
        ConnectionSource url = new JdbcConnectionSource(path);
        Dao<Student,Integer> StudentDao= DaoManager.createDao(url,Student.class);
        Dao<StudentGroup,Integer> GroupDao= DaoManager.createDao(url, StudentGroup.class);
        StudentRequest st=new StudentRequest(StudentDao);
        GroupRequest g=new GroupRequest(GroupDao,StudentDao);
        Javalin app= Javalin.create();
        StudentControllers studentControllers =new StudentControllers(StudentDao,GroupDao,st,g,app);
        GroupControllers groupControllers =new GroupControllers(StudentDao,GroupDao,st,g,app);
        studentControllers.getStudent();
        studentControllers.getStudentById();
        studentControllers.updateStudent();
        studentControllers.saveStudent();
        studentControllers.deleteStudent();
        groupControllers.getAllGroups();
        groupControllers.getGroupById();
        groupControllers.updateGroup();
        groupControllers.saveGroup();
        groupControllers.deleteGroup();
        app.start(8080);
    }
}
