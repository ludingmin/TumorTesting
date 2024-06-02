package com.tumorTest.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tumorTest.constant.JwtClaimsConstant;
import com.tumorTest.constant.RedisConstant;
import com.tumorTest.context.BaseContext;
import com.tumorTest.dto.CreateUseDto;
import com.tumorTest.dto.LoginDto;
import com.tumorTest.dto.ShowBookingResultDto;
import com.tumorTest.dto.UserDto;
import com.tumorTest.entity.Booking;
import com.tumorTest.entity.User;
import com.tumorTest.mapper.UserMapper;
import com.tumorTest.properties.JwtProperties;
import com.tumorTest.result.Result;
import com.tumorTest.service.UserService;
import com.tumorTest.uitl.JwtUtil;
import com.tumorTest.vo.LoginVo;
import com.tumorTest.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
@Slf4j
public class UserController {



    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public Result<UserVo> login(@RequestBody LoginDto loginDto){
        System.out.println(1);

        log.info("用户登录:{}",loginDto);
        return userService.userLogin(loginDto);

    }

    @PostMapping("/create")
    @ApiOperation("创建用户接口")
    public Result createUser(@RequestBody CreateUseDto createUseDto){
        log.info("创建用户:{}",createUseDto);
        return userService.createUser(createUseDto);
    }

    @PostMapping("/logout")
    @ApiOperation("用户退出登录接口")
    public Result logout(HttpServletRequest request){
        String token = request.getHeader("token");
        Boolean delete = redisTemplate.delete(RedisConstant.USER_TOKEN + token);
        if (delete)
            return Result.success("退出成功!");
        return Result.error("你未曾登录!");
    }

    @PostMapping("/findPersonalInformation")
    @ApiOperation("用户查询个人信息")
    public Result<User> findPersonalInformation(@Param("userId") Integer userId){
        User personalInformation = userService.findPersonalInformation(userId);
        return Result.success(personalInformation);
    }

    @PostMapping("/selectByBooking")
    @ApiOperation("用户查询自己的检测结果")
    public Result<ShowBookingResultDto> findUserBooking(Long bookingId){
        ShowBookingResultDto showBookingResultDto = userService.selectBooking(bookingId);

        if(showBookingResultDto == null){
            return Result.error("没有该预约单号");
        }

        return Result.success(showBookingResultDto);
    }



    @PostMapping("/message")
    @ApiOperation("用户查看个人信息的接口")
    public Result<UserDto> usermessage(){
        return userService.usermessage();
    }




}
