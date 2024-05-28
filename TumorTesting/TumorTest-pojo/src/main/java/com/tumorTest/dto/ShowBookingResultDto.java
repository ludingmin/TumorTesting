package com.tumorTest.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ShowBookingResultDto {
    //结果图片地址
    private String imgUrl;

    //医生诊断结果
    private String content;

    //检测完成时间
    private String CreateTime;
}
