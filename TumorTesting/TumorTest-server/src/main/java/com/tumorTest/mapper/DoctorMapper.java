package com.tumorTest.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tumorTest.entity.Doctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper
public interface DoctorMapper extends BaseMapper<Doctor> {
    @Select("select * from doctor")
    List<Doctor> selectAll();

    @Update("update consultation set content = #{content} where consultation_id = #{consultationId}")
    Integer updateContent(Integer consultationId, String content);
}
