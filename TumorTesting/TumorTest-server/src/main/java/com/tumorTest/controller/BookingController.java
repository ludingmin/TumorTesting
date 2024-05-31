package com.tumorTest.controller;



import com.tumorTest.entity.Booking;
import com.tumorTest.result.Result;
import com.tumorTest.service.BookingService;
import com.tumorTest.vo.BookingVo;
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
    public Result bookingadd(@Param("doctorname") String doctorname){
        System.out.println(doctorname);
        return bookingService.bookingadd(doctorname);
    }


    @PostMapping("/delete")
    @ApiOperation("取消预约")
    public Result bookingdelete(){
        return bookingService.bookingdelete();
    }

    @PostMapping("/userselect")
    @ApiOperation("查看历史预约信息")
    public Result<List<Booking>> select1(){
        return bookingService.bookingselect1();
    }


    @PostMapping("/doctorselect")
    @ApiOperation("查看医生历史预约信息")
    public Result<List<Booking>> select2(){
        return bookingService.bookingselect2();
    }


    @PostMapping("/current")
    @ApiOperation("查看当前预约人数接口")
    public Result<Integer> current(){
        return bookingService.current();
    }


    @PostMapping("/doctorbooking")
    @ApiOperation("查看当前预约自己的预约信息")
    public Result<List<BookingVo>> doctorbooking(){
        return bookingService.doctorbooking();

    }









}
