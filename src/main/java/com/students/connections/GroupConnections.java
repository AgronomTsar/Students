package com.students.connections;

import com.j256.ormlite.dao.Dao;
import com.students.deserializer.GroupDeserializer;
import com.students.models.Student;
import com.students.models.Student_group;
import com.students.service.GroupRequest;
import com.students.service.StudentRequest;
import io.javalin.Javalin;

public class GroupConnections {
    final String path="jdbc:sqlite:C:\\Users\\77012\\Desktop\\бд\\students.db";
    Dao<Student,Integer> StudentDao;
    Dao<Student_group,Integer> GroupDao;
    StudentRequest st;
    GroupRequest g;
    Javalin app;
    String groupGet="/groups";
    String groupId="/group/:id";
    String groupPatch="/groupPatch";
    String groupSave="/groupSave";
    String groupDelete="/groupDelete/:id";
    public GroupConnections(Dao<Student, Integer> studentDao, Dao<Student_group, Integer> groupDao, StudentRequest st, GroupRequest g, Javalin app) {
        StudentDao = studentDao;
        GroupDao = groupDao;
        this.st = st;
        this.g = g;
        this.app = app;
    }
    public void getAllGroups(){
        app.get(groupGet,ctx->ctx.result(String.valueOf(g.findAllGroup(ctx))));
    }
    public void getGroupById(){
        app.get(groupId,ctx->{
            ctx.result(g.groupId(Integer.parseInt(ctx.pathParam("id"))));
            ctx.status(200);
        });
    }
    public void updateGroup(){
        app.patch(groupPatch,ctx->{
            String json=ctx.body();
            Student_group student_group= GroupDeserializer.groupObjectConverter(json);
            ctx.result(g.updateGroupById(ctx,student_group.getId_group(),student_group.getName(),student_group.getSpecially_name()));
        });
    }
    public void saveGroup(){
        app.post(groupSave,ctx->{
            String json=ctx.body();
            Student_group student_group=GroupDeserializer.groupObjectConverter(json);
            ctx.result(g.saveGroup(ctx,student_group.getId_group(),student_group.getName(),student_group.getSpecially_name()));
        });
    }
    public void deleteGroup(){
        app.delete(groupDelete,ctx->g.deleteGroup(ctx,Integer.parseInt(ctx.pathParam("id"))));
    }

}
