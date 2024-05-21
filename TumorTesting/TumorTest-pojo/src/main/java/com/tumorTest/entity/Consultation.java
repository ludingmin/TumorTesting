package com.tumorTest.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;



//会诊结果
@Data
public class Consultation {

    @TableId(value = "consultation_id",type = IdType.AUTO)
    private Integer consultationId;
    private Integer bookingId;
    private Integer diagnosticStatus;
    private String content;
    private String creationTimel;
}
