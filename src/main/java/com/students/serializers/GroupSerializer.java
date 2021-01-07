package com.students.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.j256.ormlite.dao.Dao;
import com.students.models.Student;
import com.students.models.StudentGroup;

import java.io.IOException;
import java.sql.SQLException;

public class GroupSerializer extends StdSerializer<StudentGroup> {
    public GroupSerializer(Dao<StudentGroup, Integer> dao, Dao<Student, Integer> daoStudent) {
        super(StudentGroup.class);
        this.dao=dao;
        this.daoStudent=daoStudent;
    }
    private final Dao<StudentGroup,Integer> dao;
    private final Dao<Student,Integer> daoStudent;

    public void serialize(StudentGroup s, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id_group",s.getId_group());
        gen.writeStringField("name",s.getName());
        gen.writeStringField("specially_name",s.getSpecially_name());
        try {
            gen.writeObjectField("students",daoStudent.queryForEq("id_group",s.getId_group()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        gen.writeEndObject();
    }

}
