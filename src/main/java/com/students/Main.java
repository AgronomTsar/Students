package com.students;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.students.connections.GroupConnections;
import com.students.connections.StudentConnection;
import com.students.deserializer.StudentDeserializer;
import com.students.models.Student;
import com.students.models.Student_group;
import com.students.service.GroupRequest;
import com.students.service.StudentRequest;
import io.javalin.Javalin;
import javafx.scene.Group;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String path="jdbc:sqlite:C:\\Users\\77012\\Desktop\\бд\\students.db";
        ConnectionSource url = new JdbcConnectionSource(path);
        Dao<Student,Integer> StudentDao= DaoManager.createDao(url,Student.class);
        Dao<Student_group,Integer> GroupDao= DaoManager.createDao(url,Student_group.class);
        StudentRequest st=new StudentRequest(StudentDao);
        GroupRequest g=new GroupRequest(GroupDao,StudentDao);
        Javalin app= Javalin.create();
        StudentConnection studentConnection=new StudentConnection(StudentDao,GroupDao,st,g,app);
        GroupConnections groupConnections=new GroupConnections(StudentDao,GroupDao,st,g,app);
        studentConnection.getStudent();
        studentConnection.getStudentById();
        studentConnection.updateStudent();
        studentConnection.saveStudent();
        studentConnection.deleteStudent();
        groupConnections.getAllGroups();
        groupConnections.getGroupById();
        groupConnections.updateGroup();
        groupConnections.saveGroup();
        groupConnections.deleteGroup();
        app.start(8080);
    }
}
