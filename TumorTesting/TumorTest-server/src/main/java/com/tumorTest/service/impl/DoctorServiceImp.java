package com.tumorTest.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tumorTest.uitl.CommonUtil;
import com.tumorTest.dto.CreateDoctorDto;
import com.tumorTest.entity.Doctor;
import com.tumorTest.entity.User;
import com.tumorTest.excption.FormatException;
import com.tumorTest.mapper.DoctorMapper;
import com.tumorTest.mapper.UserMapper;
import com.tumorTest.result.Result;
import com.tumorTest.service.DoctorService;
import com.tumorTest.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

        //检验手机格式
        if (!CommonUtil.checkNmber(doctorDto.getNumber()))
            throw new FormatException();
        //校验用户名长度
        int length = doctorDto.getName().length();
        if (!(length>3 && length <8))
            throw new FormatException();
        User user = BeanUtil.copyProperties(doctorDto, User.class);
        String md5Password = MD5.create().digestHex(doctorDto.getPassword());
        user.setPassword(md5Password);
        user.setType(1);
        //插入用户
        userMapper.insert(user);
        Long id = user.getId();
        Doctor doctor = Doctor.builder().doctorId(id).doctorName(doctorDto.getDoctorName())
                .onboardingTime(new Date()).jobtitle(doctorDto.getJobtitle()).build();
        doctorMapper.insert(doctor);
        return Result.success("创建成功!");
    }


}
