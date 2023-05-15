package com.Student_Teacher.ManagementApplication.service;

import com.Student_Teacher.ManagementApplication.model.Student;
import com.Student_Teacher.ManagementApplication.model.Teacher;
import com.Student_Teacher.ManagementApplication.repository.StudentRepository;
import com.Student_Teacher.ManagementApplication.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    // Retrieves a list of all students
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    // Saves a new student
    public Student saveStudent(Student student) {

        return studentRepository.save(student);
    }

    // Retrieves a student by ID
    public Student getStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    // Deletes a student by ID
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).get();
        List<Teacher> teachers = teacherRepository.findByStudentsContains(student);
        for (Teacher teacher : teachers) {
            teacher.getStudents().remove(student);
            teacherRepository.save(teacher);
        }
        studentRepository.deleteById(id);
        studentRepository.delete(student);
    }
}
