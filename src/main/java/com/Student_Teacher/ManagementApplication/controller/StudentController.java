package com.Student_Teacher.ManagementApplication.controller;

import com.Student_Teacher.ManagementApplication.model.Student;
import com.Student_Teacher.ManagementApplication.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    @Autowired
    StudentService studentService;
    @GetMapping("")
    public List<Student> listStudents() {
        return studentService.listStudents();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        try {
            Student student = studentService.getStudent(id);
            return new ResponseEntity<Student>(student, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("")
    public ResponseEntity<Student> add(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.saveStudent(student);
            return ResponseEntity.created(URI.create("/students/" + savedStudent.getId())).body(savedStudent);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Student student, @PathVariable Long id) {
        try {
            Student studentExist = studentService.getStudent(id);
            student.setId(id);
            studentService.saveStudent(student);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { studentService.deleteStudent(id); }
}
