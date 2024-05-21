package com.tumorTest.controller;


import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.tumorTest.constant.JwtClaimsConstant;
import com.tumorTest.dto.LoginDto;
import com.tumorTest.entity.User;
import com.tumorTest.mapper.UserMapper;
import com.tumorTest.properties.JwtProperties;
import com.tumorTest.result.Result;
import com.tumorTest.service.UserService;
import com.tumorTest.uitl.JwtUtil;
import com.tumorTest.vo.LoginVo;
import com.tumorTest.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {


    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/login/user")
    public Result<UserVo> login(LoginDto loginDto){

        log.info("用户{}登录",loginDto);
        return userService.userLogin(loginDto);

    }


}
