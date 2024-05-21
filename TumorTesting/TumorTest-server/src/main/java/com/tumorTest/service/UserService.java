package com.tumorTest.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tumorTest.dto.CreateUseDto;
import com.tumorTest.dto.LoginDto;
import com.tumorTest.entity.User;
import com.tumorTest.result.Result;
import com.tumorTest.vo.UserVo;
import org.springframework.stereotype.Service;

public interface UserService extends IService<User> {


    Result<UserVo> userLogin(LoginDto loginDto);

    Result  createUser(CreateUseDto createUseDto);
}
