package com.tumorTest.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingVo {
    private Long id;//用户id
    private String name;//用户名
    private String number;//用户电话
    private Long bookingId;//预约单号
    private String address;//用户地址
}
