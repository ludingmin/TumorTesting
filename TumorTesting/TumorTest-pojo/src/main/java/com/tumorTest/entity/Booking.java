package com.tumorTest.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

//预约信息表
@Data
public class Booking {
    @TableId(value = "booking_id",type = IdType.AUTO)
    private Integer bookingId;
    private String name;
    private String doctorName;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    private Integer state;

    private String imgUrl;
}
