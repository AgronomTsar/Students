package com.students.controllers;

import com.j256.ormlite.dao.Dao;
import com.students.deserializer.GroupDeserializer;
import com.students.models.Student;
import com.students.models.StudentGroup;
import com.students.service.GroupService;
import com.students.service.StudentService;
import io.javalin.Javalin;

public class GroupControllers {
    Dao<Student,Integer> StudentDao;
    Dao<StudentGroup,Integer> GroupDao;
    StudentService st;
    GroupService g;
    Javalin app;
    String groupGet="/groups";
    String groupId="/group/:id";
    String groupPatch="/groupPatch";
    String groupSave="/groupSave";
    String groupDelete="/groupDelete/:id";
    public GroupControllers(Dao<Student, Integer> studentDao, Dao<StudentGroup, Integer> groupDao, StudentService st, GroupService g, Javalin app) {
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
            StudentGroup studentGroup = GroupDeserializer.groupObjectConverter(json);
            ctx.result(g.updateGroupById(ctx, studentGroup.getId_group(), studentGroup.getName(), studentGroup.getSpecially_name()));
        });
    }
    public void saveGroup(){
        app.post(groupSave,ctx->{
            String json=ctx.body();
            StudentGroup studentGroup =GroupDeserializer.groupObjectConverter(json);
            ctx.result(g.saveGroup(ctx, studentGroup.getId_group(), studentGroup.getName(), studentGroup.getSpecially_name()));
        });
    }
    public void deleteGroup(){
        app.delete(groupDelete,ctx->g.deleteGroup(ctx,Integer.parseInt(ctx.pathParam("id"))));
    }

}
