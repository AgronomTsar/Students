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

public class GroupDeserializer extends StdDeserializer<Student_group> {
    protected GroupDeserializer() {
        super(Student_group.class);
    }

    @Override
    public Student_group deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root=parser.getCodec().readTree(parser);
        int id=root.get("Id").asInt();
        String name=root.get("name").asText();
        String specialtyName=root.get("specialtyName").asText();
        return new Student_group(id,name,specialtyName);
    }
    public static Student_group groupObjectConverter(String json) throws JsonProcessingException {
        ObjectMapper om=new ObjectMapper();
        SimpleModule m=new SimpleModule();
        m.addDeserializer(Student_group.class,new GroupDeserializer());
        om.registerModule(m);
        return om.readValue(json,Student_group.class);
    }

}
