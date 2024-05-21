package com.tumorTest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tumorTest.dto.CreateUseDto;
import com.tumorTest.dto.LoginDto;
import com.tumorTest.dto.UserDto;
import com.tumorTest.entity.User;
import com.tumorTest.excption.CreateUserExcption;
import com.tumorTest.excption.LoginException;
import com.tumorTest.mapper.UserMapper;
import com.tumorTest.result.Result;
import com.tumorTest.service.UserService;
import com.tumorTest.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    RedisTemplate<String,Object>  redisTemplate;
    @Autowired
    UserMapper userMapper;


    public Result<UserVo> userLogin(LoginDto loginDto) {
        // 根据DTO查询数据库
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("name", loginDto.getUsername()));
        if (!(user.getPassword().equals(loginDto.getPassword()) && user.getType()==0)) {
            throw new LoginException("登录错误!");
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        //生成UUIdtoken
        String token = UUID.randomUUID().toString();
        //  把查询出来的用户封装成DTO，存入redis
        Map<String, Object> map = BeanUtil.beanToMap(userDto);
        redisTemplate.opsForHash().putAll("login:token:"+token,map);
        //TODO 后面防止登录攻击，可以在存token的时候，存多一个key为UserID，value为token的值。
        // 每次执行登录接口去查一下是否存在UserID。如果存在根据返回token。并续期

        // 返回结果
        // 设置VO属性
        UserVo vo = new UserVo();
        vo.setToken(token);
        BeanUtils.copyProperties(user,vo);
        return Result.success(vo);
    }


    public Result createUser(CreateUseDto createUseDto){

        User user = BeanUtil.copyProperties(createUseDto, User.class);
        int i = userMapper.insert(user);
        if (i==0)
            throw new CreateUserExcption();
        return Result.success("创建成功");

    }

}
