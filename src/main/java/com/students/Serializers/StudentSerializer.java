package com.students.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.students.models.Student;

import java.io.IOException;

public class StudentSerializer extends StdSerializer<Student> {
    protected StudentSerializer() {
        super(Student.class);
    }
    public void serialize(Student s, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",s.getId());
        gen.writeStringField("first_Name",s.getFirst_name());
        gen.writeStringField("last_Name",s.getLast_name());
        gen.writeStringField("phone",s.getPhone());
        gen.writeStringField("email",s.getEmail());
        gen.writeNumberField("id_group",s.getId_group().getId_group());
        gen.writeEndObject();
    }
}
