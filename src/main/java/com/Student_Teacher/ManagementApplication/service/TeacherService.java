package com.Student_Teacher.ManagementApplication.service;

import com.Student_Teacher.ManagementApplication.model.Student;
import com.Student_Teacher.ManagementApplication.model.Teacher;
import com.Student_Teacher.ManagementApplication.model.TeacherDetails;
import com.Student_Teacher.ManagementApplication.repository.StudentRepository;
import com.Student_Teacher.ManagementApplication.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    public List<Teacher> getTeachers(){
        return teacherRepository.findAll();
    }

    public void saveTeacher(TeacherDetails teacherDetails) {
        // Fetch the managed student objects from the database and update their fields
        List<Student> updatedStudents = new ArrayList<>();
        for (Student student : teacherDetails.getStudentList()) {
            Student managedStudent = studentRepository.findById(student.getId()).orElse(null);
            if (managedStudent == null) {
                throw new IllegalArgumentException("Invalid student ID: " + student.getId());
            }
            managedStudent.setName(student.getName());
            managedStudent.setSurname(student.getSurname());
            updatedStudents.add(managedStudent);
        }

        // Update the teacher object with the updated list of students
        teacherDetails.getTeacher().setStudents(updatedStudents);

        // save the updated teacher object
        teacherRepository.save(teacherDetails.getTeacher());
    }

    public void updateTeacher(TeacherDetails teacherUpdateDetails) {
        Teacher teacher = teacherRepository.findById(teacherUpdateDetails.getTeacher().getId()).get();
        try {
            teacher.setName(teacherUpdateDetails.getTeacher().getName());
            teacher.setStudents(teacherUpdateDetails.getStudentList());
            teacherRepository.save(teacher);
        } catch (Exception e) {
            System.out.println("Error while updating teacher information: " + e.getMessage());
        }

    }

    public Teacher getTeacher(Long id){
        return teacherRepository.findById(id).get();
    }

    public void deleteTeacher(Long id){
        Teacher teacher = teacherRepository.findById(id).get();
        List<Student> studentsCopy = new ArrayList<>(teacher.getStudents());
        for (Student student : studentsCopy) {
            teacher.getStudents().remove(student);
        }

        teacherRepository.deleteById(id);

    }
}
