package com.tumorTest.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tumorTest.dto.CreateDoctorDto;
import com.tumorTest.entity.Doctor;
import com.tumorTest.entity.User;
import com.tumorTest.mapper.DoctorMapper;
import com.tumorTest.mapper.UserMapper;
import com.tumorTest.result.Result;
import com.tumorTest.service.DoctorService;
import com.tumorTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DoctorServiceImp extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {

    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    DoctorMapper doctorMapper;

    @Transactional
    public Result createDoctor(CreateDoctorDto doctorDto){

        User user = BeanUtil.copyProperties(doctorDto, User.class);
        userMapper.insert(user);
        Long id = user.getId();
        Doctor doctor = Doctor.builder().doctorId(id).doctorName(doctorDto.getDoctorName())
                .onboardingTime(LocalDateTime.now()).jobtitle(doctorDto.getJobtitle()).build();
        doctorMapper.insert(doctor);
        return Result.success("创建成功!");
    }


}
