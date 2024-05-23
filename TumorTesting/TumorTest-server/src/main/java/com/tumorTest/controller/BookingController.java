package com.tumorTest.controller;


import com.tumorTest.dto.CreateBookingDto;
import com.tumorTest.entity.Booking;
import com.tumorTest.result.Result;
import com.tumorTest.service.BookingService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booking")
@Slf4j
public class BookingController {

    @Autowired
    BookingService bookingService;


    @PostMapping("/create")
    @ApiOperation("用户预约")
    public Result bookingadd(@RequestBody CreateBookingDto createBookingDto){
        return bookingService.bookingadd(createBookingDto);
    }


    @PostMapping("/delete")
    @ApiOperation("取消预约")
    public Result bookingdelete(@Param("name") String name){
        return bookingService.bookingdelete(name);
    }

    @PostMapping("/userselect")
    @ApiOperation("查看历史预约信息")
    public Result<List<Booking>> select1(@Param("name") String name){
        return bookingService.bookingselect1(name);
    }


    @PostMapping("/doctorselect")
    @ApiOperation("查看医生历史预约信息")
    public Result<List<Booking>> select2(@Param("name") String name){
        return bookingService.bookingselect2(name);
    }




}
