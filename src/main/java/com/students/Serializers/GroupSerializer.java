package com.students.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.j256.ormlite.dao.Dao;
import com.students.models.Student;
import com.students.models.Student_group;
import com.students.service.StudentRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupSerializer extends StdSerializer<Student_group> {
    protected GroupSerializer(Dao<Student_group, Integer> dao,Dao<Student,Integer> daoStudent) {
        super(Student_group.class);
        this.dao=dao;
        this.daoStudent=daoStudent;
    }
    private final Dao<Student_group,Integer> dao;
    private final Dao<Student,Integer> daoStudent;

    public void serialize(Student_group s, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id_group",s.getId_group());
        gen.writeStringField("name",s.getName());
        gen.writeStringField("specially_name",s.getSpecially_name());
        try {
            gen.writeObjectField("students",StudentSerializer.studentList(daoStudent.queryForEq("id_group",s.getId_group())));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        gen.writeEndObject();
    }
    static public List<String> listSerializer(ArrayList<Student_group> groups,Dao<Student_group, Integer> dao,Dao<Student,Integer> daoStudent) throws JsonProcessingException {
        ArrayList<String> groupJson=new ArrayList<String>();
        ObjectMapper om=new ObjectMapper();
        SimpleModule m=new SimpleModule();
        m.addSerializer(Student_group.class,new GroupSerializer(dao,daoStudent));
        om.registerModule(m);
        int count=0;
        while(count<groups.size()){
            groupJson.add(om.writeValueAsString(groups.get(count)));
            count++;
        }
        return groupJson;
    }
}
