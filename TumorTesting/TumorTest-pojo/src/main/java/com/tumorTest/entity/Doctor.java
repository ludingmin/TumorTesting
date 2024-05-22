package com.tumorTest.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


//医生
@Data
public class Doctor {
    @TableId(value = "doctor_id",type = IdType.AUTO)
    private Integer doctorId;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDateTime onboardingTime;
    private String jobtitle;

    private String doctorName;

}
