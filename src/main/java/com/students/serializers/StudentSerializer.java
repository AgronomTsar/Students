package com.students.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.students.models.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentSerializer extends StdSerializer<Student> {
    public StudentSerializer() {
        super(Student.class);
    }
    public void serialize(Student s, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",s.getId());
        gen.writeStringField("first_Name",s.getFirst_name());
        gen.writeStringField("last_Name",s.getLast_name());
        gen.writeStringField("phone",s.getPhone());
        gen.writeStringField("email",s.getEmail());
        gen.writeStringField("id_group",s.getId_group().getName());
        gen.writeEndObject();
    }
    static public List<String> studentList(List<Student> students) throws JsonProcessingException {
        ObjectMapper om=new ObjectMapper();
        SimpleModule m=new SimpleModule();
        m.addSerializer(Student.class,new StudentSerializer());
        om.registerModule(m);
        ArrayList<String> string=new ArrayList<>();
        int count=0;
        while (count<students.size()){
            string.add(om.writeValueAsString(students.get(count)));
            count++;
        }
        return string;
    }
    static public String studentStringConverter(Student student) throws JsonProcessingException {
        ObjectMapper om=new ObjectMapper();
        SimpleModule m=new SimpleModule();
        m.addSerializer(Student.class,new StudentSerializer());
        om.registerModule(m);
        return om.writeValueAsString(student);
    }
}