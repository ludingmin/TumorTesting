package com.tumorTest.controller;


import com.tumorTest.dto.CreateDoctorDto;
import com.tumorTest.result.Result;
import com.tumorTest.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @PostMapping("/create")
    public Result createDoctor( CreateDoctorDto createDoctorDto){
        return doctorService.createDoctor(createDoctorDto);
    }

}
