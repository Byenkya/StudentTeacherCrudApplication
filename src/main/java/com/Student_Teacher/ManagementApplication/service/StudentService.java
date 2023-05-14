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
    public List<Student> listStudents() {
        return studentRepository.findAll();
    }
    public Student saveStudent(Student student) {

        return studentRepository.save(student);
    }
    public Student getStudent(Long id) {
        return studentRepository.findById(id).get();
    }
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
