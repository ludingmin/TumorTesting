package com.tumorTest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tumorTest.dto.CreateBookingDto;
import com.tumorTest.entity.Booking;
import com.tumorTest.result.Result;


import java.util.List;

public interface BookingService extends IService<Booking> {
    //预约
    Result bookingadd(CreateBookingDto createBookingDto);



    //取消预约
    Result bookingdelete(String name);


    Result<List<Booking>> bookingselect1(String name);

    Result<List<Booking>> bookingselect2(String name);
}
