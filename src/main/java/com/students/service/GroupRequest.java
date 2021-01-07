package com.students.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.j256.ormlite.dao.Dao;
import com.students.serializers.GroupSerializer;
import com.students.models.Student;
import com.students.models.StudentGroup;
import com.students.serializers.StudentSerializer;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupRequest {
    private final Dao<StudentGroup,Integer> dao;
    private final Dao<Student,Integer> daoStudent;
    public GroupRequest(Dao<StudentGroup, Integer> dao, Dao<Student,Integer> daoStudent) {
        this.dao = dao;
        this.daoStudent=daoStudent;
    }
    public StudentGroup findGroupById(int id) throws SQLException, JsonProcessingException {
        return dao.queryForId(id);
    }
    public String findAllGroup(Context ctx) throws SQLException, JsonProcessingException {
        ArrayList<StudentGroup> groups= (ArrayList<StudentGroup>) dao.queryForAll();
//        ArrayList<String> groupJson=new ArrayList<String>();
        ObjectMapper om=new ObjectMapper();
        SimpleModule m=new SimpleModule();
        m.addSerializer(StudentGroup.class,new GroupSerializer(dao,daoStudent));
        m.addSerializer(Student.class, new StudentSerializer());
        om.registerModule(m);
//        int count=0;
//        while(count<groups.size()){
//            groupJson.add(om.writeValueAsString(groups.get(count)));
//            count++;
//        }
        ctx.status(200);
        return om.writeValueAsString(groups);
    }
    public String groupId(int id) throws SQLException, JsonProcessingException {
        StudentGroup studentGroup =dao.queryForId(id);
        ObjectMapper om=new ObjectMapper();
        SimpleModule m=new SimpleModule();
        m.addSerializer(StudentGroup.class,new GroupSerializer(dao,daoStudent));
        m.addSerializer(Student.class, new StudentSerializer());
        om.registerModule(m);
        return om.writeValueAsString(studentGroup);
    }
    public String updateGroupById(Context ctx,int id_group,String name,String specialty_name) throws SQLException, JsonProcessingException {
        StudentGroup studentGroup =dao.queryForId(id_group);
        studentGroup.setName(name);
        studentGroup.setSpecially_name(specialty_name);
        dao.update(studentGroup);
        ctx.status(200);
        return groupId(id_group);
    }
    public String saveGroup(Context ctx,int id_group,String name,String specialty_name) throws SQLException, JsonProcessingException {
        StudentGroup studentGroup =new StudentGroup(id_group,name,specialty_name);
        dao.create(studentGroup);
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
