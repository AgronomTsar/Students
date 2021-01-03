package com.students.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.j256.ormlite.dao.Dao;
import com.students.models.Student;
import com.students.models.Student_group;

import java.io.IOException;
import java.sql.SQLException;

public class StudentDeserializer extends StdDeserializer<Student> {
    protected StudentDeserializer(Dao<Student_group, Integer> dao) {
        super(Student.class);
        this.daoGroup = dao;
    }
    private final Dao<Student_group,Integer> daoGroup;
    @Override
    public Student deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root=parser.getCodec().readTree(parser);
        int id=root.get("Id").asInt();
        String firstName=root.get("firstName").asText();
        String lastName=root.get("lastName").asText();
        String phone=root.get("phone").asText();
        String email=root.get("email").asText();
        int id_group=root.get("id_group").asInt();
        try {
            return new Student(id,firstName,lastName,phone,email,daoGroup.queryForId(id_group));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
    static public Student studentObjectConverter(String json,Dao<Student_group,Integer> daoGroup) throws JsonProcessingException {
        ObjectMapper om=new ObjectMapper();
        SimpleModule m=new SimpleModule();
        m.addDeserializer(Student.class,new StudentDeserializer(daoGroup));
        om.registerModule(m);
        return om.readValue(json,Student.class);
    }

}
