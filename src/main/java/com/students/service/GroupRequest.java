package com.students.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.j256.ormlite.dao.Dao;
import com.students.serializers.GroupSerializer;
import com.students.models.Student;
import com.students.models.Student_group;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupRequest {
    private final Dao<Student_group,Integer> dao;
    private final Dao<Student,Integer> daoStudent;
    public GroupRequest(Dao<Student_group, Integer> dao,Dao<Student,Integer> daoStudent) {
        this.dao = dao;
        this.daoStudent=daoStudent;
    }
    public Student_group findGroupById(int id) throws SQLException, JsonProcessingException {
        return dao.queryForId(id);
    }
    public List<String> findAllGroup(Context ctx) throws SQLException, JsonProcessingException {
        ArrayList<Student_group> groups= (ArrayList<Student_group>) dao.queryForAll();
        ArrayList<String> s= (ArrayList<String>) GroupSerializer.listSerializer(groups,dao,daoStudent);
        ctx.status(200);
        return s;
    }
    public String groupId(int id) throws SQLException, JsonProcessingException {
        Student_group student_group=dao.queryForId(id);
        return GroupSerializer.groupStringConverter(student_group,dao,daoStudent);
    }
    public String updateGroupById(Context ctx,int id_group,String name,String specialty_name) throws SQLException, JsonProcessingException {
        Student_group student_group=dao.queryForId(id_group);
        student_group.setName(name);
        student_group.setSpecially_name(specialty_name);
        dao.update(student_group);
        ctx.status(200);
        return groupId(id_group);
    }
    public String saveGroup(Context ctx,int id_group,String name,String specialty_name) throws SQLException, JsonProcessingException {
        Student_group student_group=new Student_group(id_group,name,specialty_name);
        dao.create(student_group);
        ctx.status(201);
        return groupId(id_group);
    }
    public void deleteGroup(Context ctx,int id) throws SQLException {
        List<Student> students=daoStudent.queryForEq("id_group",id);
        if(students.size()==0){
            dao.deleteById(id);
            ctx.status(204);
        }
        else{
            ctx.status(400);
        }

    }




}
