package com.Student_Teacher.ManagementApplication.model;

import java.util.List;
public class TeacherDetails {
    Teacher teacher;
    List<Student> studentList;
    public TeacherDetails(Teacher teacher, List<Student> students) {
        this.teacher = teacher;
        this.studentList = students;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public List<Student> getStudentList() {
        return studentList;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
    @Override
    public String toString() {
        return "TeacherDetails{" +
                "teacher=" + teacher +
                ", studentList=" + studentList +
                '}';
    }
}
