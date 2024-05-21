package com.tumorTest.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


//医生
@Data
public class Doctor {
    @TableId(value = "doctor_id",type = IdType.AUTO)
    private Integer doctorId;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Data onboardingTime;
    private String jobtitle;

}
