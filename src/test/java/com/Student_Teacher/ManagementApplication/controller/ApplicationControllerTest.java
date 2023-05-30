package com.Student_Teacher.ManagementApplication.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ApplicationControllerTest {

    @Test
    public void testIndex() {
        ApplicationController controller = new ApplicationController();

        // Mock the Model object
        Model model = mock(Model.class);

        // Invoke the index method
        String result = controller.index();

        // Verify the returned view name
        assertEquals("index", result);
    }
}
