package com.tumorTest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tumorTest.entity.Patient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
}
