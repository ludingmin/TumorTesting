package com.tumorTest.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.tumorTest.entity.Booking;
import com.tumorTest.result.Result;
import com.tumorTest.vo.BookingVo;


import java.util.List;

public interface BookingService extends IService<Booking> {
    //预约
    Result bookingadd(String doctor);

    //取消预约
    Result bookingdelete();


    //
    Result<List<Booking>> bookingselect1();

    Result<List<Booking>> bookingselect2();

    Result<Integer> current();

    Result<List<BookingVo>> doctorbooking();
}
