package com.Student_Teacher.ManagementApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    // Renders the index.html file
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
