package com.students;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
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
        app.get("/student",ctx->ctx.result(String.valueOf(st.findAllStudents(ctx))));
        app.get("/student/:id",ctx->{
            ctx.result(st.findStudentById(Integer.parseInt(ctx.pathParam("id"))));
            ctx.status(200);

        });
        app.patch("/student/:id/:first_name/:last_name/:phone/:email/:id_group",
                ctx->ctx.result(st.updateStudentById(ctx,Integer.parseInt(ctx.pathParam("id")),
                        ctx.pathParam("first_name"),
                        ctx.pathParam("last_name"),
                        ctx.pathParam("phone"),
                        ctx.pathParam("email"),
                        g.findGroupById(Integer.parseInt(ctx.pathParam("id_group"))))));
        app.post("/student/:id/:first_name/:last_name/:phone/:email/:id_group",ctx->
                ctx.result(st.saveStudent(ctx,
                        Integer.parseInt(ctx.pathParam("id")),
                        ctx.pathParam("first_name"),
                        ctx.pathParam("last_name"),
                        ctx.pathParam("phone"),
                        ctx.pathParam("email"),
                        g.findGroupById(Integer.parseInt(ctx.pathParam("id_group"))))));
        app.delete("/student/:id",ctx->{
            st.deleteStudent(ctx,Integer.parseInt(ctx.pathParam("id")));
            ctx.status(204).result("204");
        });
        app.get("/groups",ctx->ctx.result(String.valueOf(g.findAllGroup(ctx))));
        app.get("/group/:id",ctx->{
            ctx.result(g.groupId(Integer.parseInt(ctx.pathParam("id"))));
            ctx.status(200);
        });
        app.patch("/group/:id/:name/:specialty_name",ctx->ctx.result(g.updateGroupById(
                ctx,
                Integer.parseInt(ctx.pathParam("id")),
                ctx.pathParam("name"),
                ctx.pathParam("specialty_name"))));
        app.post("/group/:id/:name/:specialty_name",ctx->
                ctx.result(g.saveGroup(
                        ctx,
                        Integer.parseInt(ctx.pathParam("id")),
                        ctx.pathParam("name"),
                        ctx.pathParam("specialty_name"))));
        app.delete("/group/:id",ctx->g.deleteGroup(ctx,Integer.parseInt(ctx.pathParam("id"))));
        app.start(8080);
    }
}
