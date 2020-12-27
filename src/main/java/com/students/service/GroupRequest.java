package com.students.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.j256.ormlite.dao.Dao;
import com.students.Serializers.GroupSerializer;
import com.students.Serializers.StudentSerializer;
import com.students.models.Student;
import com.students.models.Student_group;

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
    public Student_group findGroupById(int id) throws SQLException {
        return dao.queryForId(id);
    }
    public List<String> findAllGroup() throws SQLException, JsonProcessingException {
        ArrayList<Student_group> groups= (ArrayList<Student_group>) dao.queryForAll();
        ArrayList<String> s= (ArrayList<String>) GroupSerializer.listSerializer(groups,dao,daoStudent);
        return s;
    }

}
