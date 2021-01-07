package com.students.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.students.models.studentGroup;

import java.io.IOException;

public class GroupDeserializer extends StdDeserializer<studentGroup> {
    protected GroupDeserializer() {
        super(studentGroup.class);
    }

    @Override
    public studentGroup deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root=parser.getCodec().readTree(parser);
        int id=root.get("Id").asInt();
        String name=root.get("name").asText();
        String specialtyName=root.get("specialtyName").asText();
        return new studentGroup(id,name,specialtyName);
    }
    public static studentGroup groupObjectConverter(String json) throws JsonProcessingException {
        ObjectMapper om=new ObjectMapper();
        SimpleModule m=new SimpleModule();
        m.addDeserializer(studentGroup.class,new GroupDeserializer());
        om.registerModule(m);
        return om.readValue(json, studentGroup.class);
    }

}
