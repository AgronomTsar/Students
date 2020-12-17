package com.students.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.students.models.Student;
import com.students.models.Student_group;

import java.io.IOException;

public class GroupSerializer extends StdSerializer<Student_group> {
    protected GroupSerializer() {
        super(Student_group.class);
    }

    public void serialize(Student_group s, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id_group",s.getId_group());
        gen.writeStringField("name",s.getName());
        gen.writeStringField("specially_name",s.getSpecially_name());
        gen.writeEndObject();
    }
}
