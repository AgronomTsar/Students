package com.students.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.students.models.StudentGroup;

import java.io.IOException;

public class GroupDeserializer extends StdDeserializer<StudentGroup> {
    protected GroupDeserializer() {
        super(StudentGroup.class);
    }

    @Override
    public StudentGroup deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root=parser.getCodec().readTree(parser);
        int id=root.get("id").asInt();
        String name=root.get("name").asText();
        String specialtyName=root.get("specialty_name").asText();
        return new StudentGroup(id,name,specialtyName);
    }
    public static StudentGroup groupObjectConverter(String json) throws JsonProcessingException {
        ObjectMapper om=new ObjectMapper();
        SimpleModule m=new SimpleModule();
        m.addDeserializer(StudentGroup.class,new GroupDeserializer());
        om.registerModule(m);
        return om.readValue(json, StudentGroup.class);
    }

}
