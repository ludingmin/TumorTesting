package com.tumorTest.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tumorTest.entity.Consultation;
import com.tumorTest.mapper.ConsultationMapper;
import com.tumorTest.service.ConsultationService;
import org.springframework.stereotype.Service;

@Service
public class ConsulttationServiceImp  extends ServiceImpl<ConsultationMapper, Consultation> implements ConsultationService {
}
