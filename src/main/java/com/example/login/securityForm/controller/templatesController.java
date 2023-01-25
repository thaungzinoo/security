package com.example.login.securityForm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class templatesController {

    @GetMapping("login")
    public String getLoginView(){
        return "login";//this return name need to be the same with login.html in the templates
    }
    @GetMapping("courses")
    public String getCourses(){
        return "courses";//this return name need to be the same with login.html in the templates
    }

}
