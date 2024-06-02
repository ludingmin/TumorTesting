package com.tumorTest.controller;


import com.tumorTest.dto.CreateDoctorDto;
import com.tumorTest.entity.Doctor;
import com.tumorTest.result.Result;
import com.tumorTest.service.DoctorService;
import com.tumorTest.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/doctor")
@Api(tags = "医生相关接口")
@Slf4j
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @Autowired
    FileService fileService;

    @PostMapping("/create")
    public Result createDoctor( CreateDoctorDto createDoctorDto){
        return doctorService.createDoctor(createDoctorDto);
    }


    @PostMapping("/reload")
    @ApiOperation("文件上传接口")
    public Result fileReload(MultipartFile file){
        return fileService.fileReload(file);
    }

    /**
     * 查询所有医生
     * @return
     */
    @PostMapping("/allDoctor")
    @ApiOperation("查询所有医生")
    public Result<List<Doctor>> selectALlDoctor(){
        List<Doctor> doctorList = doctorService.selectAllDoctor();
        return Result.success(doctorList);
    }

}
