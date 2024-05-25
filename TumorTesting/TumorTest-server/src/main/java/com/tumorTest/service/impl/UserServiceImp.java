package com.tumorTest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tumorTest.uitl.CommonUtil;
import com.tumorTest.constant.RedisConstant;
import com.tumorTest.dto.CreateUseDto;
import com.tumorTest.dto.LoginDto;
import com.tumorTest.dto.UserDto;
import com.tumorTest.entity.User;
import com.tumorTest.excption.CreateUserExcption;
import com.tumorTest.excption.FormatException;
import com.tumorTest.excption.LoginException;
import com.tumorTest.mapper.UserMapper;
import com.tumorTest.result.Result;
import com.tumorTest.service.UserService;
import com.tumorTest.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    RedisTemplate<String,Object>  redisTemplate;
    @Autowired
    UserMapper userMapper;


    public Result<UserVo> userLogin(LoginDto loginDto) {

        // 根据DTO查询数据库
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("name", loginDto.getUsername()));
        String md5Password = MD5.create().digestHex(loginDto.getPassword());
        if (!(user.getPassword().equals(md5Password) && user.getType()==0)) {
            throw new LoginException("登录错误!");
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        //生成UUIdtoken
        String token = UUID.randomUUID().toString();
        //  把查询出来的用户封装成DTO，存入redis
        Map<String, Object> map = BeanUtil.beanToMap(userDto);
        redisTemplate.opsForHash().putAll(RedisConstant.USER_TOKEN +token,map);
        redisTemplate.expire(RedisConstant.USER_TOKEN+token,RedisConstant.TOKEN_TIME, TimeUnit.MINUTES);
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

        //检验传入的手机号
        if (!CommonUtil.checkNmber(createUseDto.getNumber()) ) {
            throw new FormatException();
        }
        //用户名的长度不能少于3，大于8
        int length = createUseDto.getName().length();
        if (!(length>3 && length<8 ))
            throw new FormatException();

        String md5Password = MD5.create().digestHex(createUseDto.getPassword());

        User user = BeanUtil.copyProperties(createUseDto, User.class);
        user.setType(0);
        user.setPassword(md5Password);
        int i = userMapper.insert(user);
        if (i==0)
            throw new CreateUserExcption();
        return Result.success("创建成功");

    }

}
