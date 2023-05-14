package com.Student_Teacher.ManagementApplication.controller;

import com.Student_Teacher.ManagementApplication.model.Student;
import com.Student_Teacher.ManagementApplication.repository.StudentRepository;
import com.Student_Teacher.ManagementApplication.service.StudentService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    // records to test with
    Student student1 = new Student(  "Micheal", "Ocen");
    Student student2 = new Student("Gwakamola", "Chereyo");
    Student student3 = new Student("Stephen", "Sejjussa");
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }
    @Test
    public void testListStudents() throws Exception {
        // Arrange
        List<Student> records = new ArrayList<>(Arrays.asList(student1, student2, student3));

        // Act
        Mockito.when(studentController.listStudents()).thenReturn(records);

        // Assert
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Stephen")));
    }
    @Test
    public void testGetExistingStudent() throws Exception {
        // Arrange
        Mockito.when(studentService.getStudent(student1.getId())).thenReturn(student1);

        // Act
        ResponseEntity<Student> response = studentController.get(student1.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student1, response.getBody());
    }


    @Test
    public void testGetNonExistingStudent() {
        // Arrange
        Mockito.when(studentService.getStudent(2L)).thenThrow(NoSuchElementException.class);

        // Act
        ResponseEntity<Student> response = studentController.get(2L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testAddStudent() {
        // Arrange
        Mockito.when(studentService.saveStudent(student1)).thenReturn(student1);

        // Act
        ResponseEntity<Student> response = studentController.add(student1);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(student1, response.getBody());
        assertTrue(response.getHeaders().containsKey("Location"));
        assertEquals(URI.create("/students/" + student1.getId()), response.getHeaders().getLocation());
        verify(studentService, times(1)).saveStudent(student1);
    }

    @Test
    public void testUpdateExistingStudent() {
        // Arrange
        Mockito.when(studentService.getStudent(student1.getId())).thenReturn(student1);

        // Act
        ResponseEntity<?> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        ResponseEntity<?> actualResponse = studentController.update(student1, student1.getId());

        // Assert
        assertEquals(expectedResponse, actualResponse);
        verify(studentService, times(1)).getStudent(student1.getId());
    }

    @Test
    public void testDeleteStudent() {
        // Call the delete() method of the StudentController with a student ID
        studentController.delete(student1.getId());

        // Verify that the deleteStudent() method of the StudentService was called once with the correct student ID
        verify(studentService, times(1)).deleteStudent(student1.getId());
    }

}
