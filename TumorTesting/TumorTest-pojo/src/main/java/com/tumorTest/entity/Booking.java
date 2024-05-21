package com.tumorTest.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

//预约信息表
@Data
public class Booking {
    @TableId(value = "booking_id",type = IdType.AUTO)
    private Integer bookingId;
    private Integer id;
    private Integer doctorId;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Data data;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Data time;
    private Integer state;
}
