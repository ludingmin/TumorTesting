package com.tumorTest.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


//医生
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {

    private Long doctorId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDateTime onboardingTime;

    private String jobtitle;

    private String doctorName;

}
