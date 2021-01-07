package com.students.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="Student_group")
public class StudentGroup {
    @DatabaseField(id=true)
    int id_group;
    @DatabaseField
    String name;
    @DatabaseField
    String specially_name;
    public StudentGroup(){

    }

    public StudentGroup(int id_group, String name, String specially_name) {
        this.id_group = id_group;
        this.name = name;
        this.specially_name = specially_name;
    }

    public int getId_group() {
        return id_group;
    }

    public void setId_group(int id_group) {
        this.id_group = id_group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecially_name() {
        return specially_name;
    }

    public void setSpecially_name(String specially_name) {
        this.specially_name = specially_name;
    }

    @Override
    public String toString() {
        return "Student_group{" +
                "id_group=" + id_group +
                ", name='" + name + '\'' +
                ", specially_name='" + specially_name + '\'' +
                '}';
    }
}
