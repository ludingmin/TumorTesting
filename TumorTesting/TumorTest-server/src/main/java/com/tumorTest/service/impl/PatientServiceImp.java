package com.tumorTest.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tumorTest.entity.Patient;
import com.tumorTest.mapper.PatientMapper;
import com.tumorTest.service.PatientService;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImp extends ServiceImpl<PatientMapper, Patient> implements PatientService {
}
