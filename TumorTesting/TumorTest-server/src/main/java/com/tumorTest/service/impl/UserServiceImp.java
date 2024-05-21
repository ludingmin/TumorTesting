package com.tumorTest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tumorTest.dto.LoginDto;
import com.tumorTest.entity.User;
import com.tumorTest.mapper.UserMapper;
import com.tumorTest.result.Result;
import com.tumorTest.service.UserService;
import com.tumorTest.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    RedisTemplate<String,Object>  redisTemplate;
    @Autowired
    UserMapper userMapper;
    public Result<UserVo> userLogin(LoginDto loginDto) {
        //TODO 根据DTO查询数据库
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("name", loginDto.getUsername()));
        if (!(user.getPassword().equals(loginDto.getPassword()) && user.getType()==0)) {
            return Result.error("密码或用户名错误");
        }
        //TODO 设置VO属性
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user,vo);
        //TODO 生成UUIdtoken
        String token = UUID.randomUUID().toString();
        // TODO  把查询出来的用户封装成DTO，存入redis，设置有效期（Key：”user：{id} value：“UUId”）
        vo.setToken(token);
        redisTemplate.opsForValue().set("login:token:"+token,vo);
        //TODO 返回结果
        return Result.success(vo);
    }
}
