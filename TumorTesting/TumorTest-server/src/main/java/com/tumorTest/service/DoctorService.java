package com.tumorTest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tumorTest.dto.CreateDoctorDto;
import com.tumorTest.entity.Doctor;
import com.tumorTest.result.Result;

import java.util.List;

public interface DoctorService extends IService<Doctor> {
    public Result createDoctor(CreateDoctorDto doctorDto);

    /**
     * 查询所有医生
     * @return
     */
    public List<Doctor> selectAllDoctor();

    /**
     * 医生写评语
     * @param consultationId
     * @param content
     * @return
     */
    Result writeContent(Integer consultationId, String content);
}
