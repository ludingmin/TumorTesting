package com.tumorTest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tumorTest.dto.CreateDoctorDto;
import com.tumorTest.entity.Doctor;
import com.tumorTest.result.Result;

public interface DoctorService extends IService<Doctor> {
    public Result createDoctor(CreateDoctorDto doctorDto);
}
