package com.tumorTest.controller;


import com.tumorTest.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class AdminController {

    @Autowired
    private JwtProperties jwtProperties;


}
