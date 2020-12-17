package com.students.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Student")
public class Student {
    @DatabaseField(id=true)
    int id;
    @DatabaseField
    String first_name;
    @DatabaseField
    String last_name;
    @DatabaseField
    String phone;
    @DatabaseField
    String email;
    @DatabaseField(foreign=true, foreignAutoRefresh = true)
    Student_group id_group;

    public Student(int id, String first_name, String last_name, String phone, String email, Student_group id_group) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.id_group = id_group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Student_group getId_group() {
        return id_group;
    }

    public void setId_group(Student_group id_group) {
        this.id_group = id_group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", id_group=" + id_group +
                '}';
    }
}
