package com.tumorTest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tumorTest.entity.User;
import com.tumorTest.mapper.UserMapper;
import com.tumorTest.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
}
