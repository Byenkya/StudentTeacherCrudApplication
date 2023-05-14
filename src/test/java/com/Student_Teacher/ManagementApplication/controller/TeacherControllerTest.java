package com.Student_Teacher.ManagementApplication.controller;

import com.Student_Teacher.ManagementApplication.model.Student;
import com.Student_Teacher.ManagementApplication.model.Teacher;
import com.Student_Teacher.ManagementApplication.model.TeacherDetails;
import com.Student_Teacher.ManagementApplication.repository.TeacherRepository;
import com.Student_Teacher.ManagementApplication.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.NoSuchElementException;
@RunWith(MockitoJUnitRunner.class)
public class TeacherControllerTest {
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private TeacherService teacherService;
    @InjectMocks
    private TeacherController teacherController;

    // records to test with
    Teacher teacher1 = new Teacher("Masaba John");
    Teacher teacher2 = new Teacher("Odong Sam");

    Student student1 = new Student(  "Micheal", "Ocen");
    Student student2 = new Student("Gwakamola", "Chereyo");
    Student student3 = new Student("Stephen", "Sejjussa");
    ArrayList<Student> students = new ArrayList<>(Arrays.asList(student1, student2, student3));
    TeacherDetails teacherDetails = new TeacherDetails(teacher1, students);
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }
    @Test
    public void testListTeachers() {
        // Arrange
        List<Teacher> teachers = new ArrayList<>(Arrays.asList(teacher1, teacher2));
        Mockito.when(teacherService.getTeachers()).thenReturn(teachers);

        // Act
        List<Teacher> result = teacherController.listTeachers();

        // Assert
        assertEquals(teachers, result);
        verify(teacherService, times(1)).getTeachers();
    }
    @Test
    public void testGetExistingTeacher() {
        // Arrange
        Mockito.when(teacherService.getTeacher(teacher1.getId())).thenReturn(teacher1);

        // Act
        ResponseEntity<Teacher> expectedResponse = new ResponseEntity<>(teacher1, HttpStatus.OK);
        ResponseEntity<Teacher> actualResponse = teacherController.get(teacher1.getId());

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(teacherService, times(1)).getTeacher(teacher1.getId());
    }

    @Test
    public void testGetNonExistingTeacher() {
        // Arrange
        Mockito.when(teacherService.getTeacher(2L)).thenThrow(NoSuchElementException.class);

        // Act
        ResponseEntity<Teacher> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseEntity<Teacher> actualResponse = teacherController.get(2L);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(teacherService, times(1)).getTeacher(2L);
    }
    @Test
    public void testCreateTeacher() {
        // Arrange
        Mockito.doNothing().when(teacherService).saveTeacher(teacherDetails);

        // Act
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        ResponseEntity<?> actualResponse = teacherController.createTeacher(teacherDetails);

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(teacherService, times(1)).saveTeacher(teacherDetails);
    }
    @Test
    public void testUpdateExistingTeacher() {
        // Arrange
        Mockito.when(teacherService.getTeacher(teacher1.getId())).thenReturn(teacher1);
        Mockito.doNothing().when(teacherService).updateTeacher(teacherDetails);

        // Act
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        ResponseEntity<?> actualResponse = teacherController.update(teacherDetails, teacher1.getId());

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(teacherService, times(1)).getTeacher(teacher1.getId());
        verify(teacherService, times(1)).updateTeacher(teacherDetails);
    }
    @Test
    public void testDeleteTeacher() {
        // Call the delete() method of the TeacherController with a teacher ID
        teacherController.delete(teacher1.getId());

        // Verify that the deleteTeacher() method of the TeacherService was called once with the correct teacher ID
        verify(teacherService, times(1)).deleteTeacher(teacher1.getId());
    }

}
