package com.tumorTest.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tumorTest.entity.Doctor;
import com.tumorTest.mapper.DoctorMapper;
import com.tumorTest.service.DoctorService;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImp extends ServiceImpl<DoctorMapper, Doctor> implements DoctorService {
}
