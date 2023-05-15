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

    // Records to test with
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
        // Creating a list of teachers
        List<Teacher> teachers = new ArrayList<>(Arrays.asList(teacher1, teacher2));

        // Mocking the behavior of teacherService.getTeachers() to return the list of teachers
        Mockito.when(teacherService.getTeachers()).thenReturn(teachers);

        // Act
        // Invoking the listTeachers() method on the teacherController
        List<Teacher> result = teacherController.listTeachers();

        // Assert
        // Verifying that the returned list of teachers matches the expected list
        assertEquals(teachers, result);

        // Verifying that the getTeachers() method of teacherService is called exactly once
        verify(teacherService, times(1)).getTeachers();
    }

    @Test
    public void testGetExistingTeacher() {
        // Arrange
        // Mocking the behavior of teacherService.getTeacher() to return teacher1 when called with its ID
        Mockito.when(teacherService.getTeacher(teacher1.getId())).thenReturn(teacher1);

        // Act
        // Creating expected and actual response entities with teacher1 and HttpStatus.OK
        ResponseEntity<Teacher> expectedResponse = new ResponseEntity<>(teacher1, HttpStatus.OK);
        ResponseEntity<Teacher> actualResponse = teacherController.get(teacher1.getId());

        // Assert
        // Verifying that the actual response matches the expected response
        assertEquals(expectedResponse, actualResponse);

        // Verifying that the getTeacher() method of teacherService is called exactly once with the ID of teacher1
        verify(teacherService, times(1)).getTeacher(teacher1.getId());
    }

    @Test
    public void testGetNonExistingTeacher() {
        // Arrange
        // Mocking the behavior of teacherService.getTeacher() to throw a NoSuchElementException when called with 2L
        Mockito.when(teacherService.getTeacher(2L)).thenThrow(NoSuchElementException.class);

        // Act
        // Creating expected and actual response entities with HttpStatus.NOT_FOUND
        ResponseEntity<Teacher> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseEntity<Teacher> actualResponse = teacherController.get(2L);

        // Assert
        // Verifying that the actual response matches the expected response
        assertEquals(expectedResponse, actualResponse);

        // Verifying that the getTeacher() method of teacherService is called exactly once with 2L
        verify(teacherService, times(1)).getTeacher(2L);
    }

    @Test
    public void testCreateTeacher() {
        // Arrange
        // Mocking the behavior of teacherService.saveTeacher() to do nothing when called with teacherDetails
        Mockito.doNothing().when(teacherService).saveTeacher(teacherDetails);

        // Act
        // Creating expected and actual response entities with HttpStatus.OK
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        ResponseEntity<?> actualResponse = teacherController.createTeacher(teacherDetails);

        // Assert
        // Verifying that the actual response matches the expected response
        assertEquals(expectedResponse, actualResponse);

        // Verifying that the saveTeacher() method of teacherService is called exactly once with teacherDetails
        verify(teacherService, times(1)).saveTeacher(teacherDetails);
    }

    @Test
    public void testUpdateExistingTeacher() {
        // Arrange
        // Mocking the behavior of teacherService.getTeacher() to return teacher1 when called with its ID
        Mockito.when(teacherService.getTeacher(teacher1.getId())).thenReturn(teacher1);

        // Mocking the behavior of teacherService.updateTeacher() to do nothing when called with teacherDetails
        Mockito.doNothing().when(teacherService).updateTeacher(teacherDetails);

        // Act
        // Creating expected and actual response entities with HttpStatus.OK
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        ResponseEntity<?> actualResponse = teacherController.update(teacherDetails, teacher1.getId());

        // Assert
        // Verifying that the actual response matches the expected response
        assertEquals(expectedResponse, actualResponse);

        // Verifying that the getTeacher() method of teacherService is called exactly once with the ID of teacher1
        verify(teacherService, times(1)).getTeacher(teacher1.getId());

        // Verifying that the updateTeacher() method of teacherService is called exactly once with teacherDetails
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
