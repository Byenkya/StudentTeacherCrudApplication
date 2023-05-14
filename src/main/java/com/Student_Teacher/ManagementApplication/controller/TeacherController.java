package com.Student_Teacher.ManagementApplication.controller;

import com.Student_Teacher.ManagementApplication.model.Teacher;
import com.Student_Teacher.ManagementApplication.model.TeacherDetails;
import com.Student_Teacher.ManagementApplication.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
@RestController
@RequestMapping("api/v1/teachers")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @GetMapping("")
    public List<Teacher> listTeachers() { return teacherService.getTeachers(); }
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> get(@PathVariable Long id) {
        try {
            Teacher teacher = teacherService.getTeacher(id);
            return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Teacher>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("")
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDetails teacherDetails) {
        try {
            teacherService.saveTeacher(teacherDetails);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody TeacherDetails teacherDetails, @PathVariable Long id){
        try {
            Teacher teacherExists = teacherService.getTeacher(id);
            teacherDetails.getTeacher().setId(id);
            teacherService.updateTeacher(teacherDetails);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { teacherService.deleteTeacher(id); }
}
