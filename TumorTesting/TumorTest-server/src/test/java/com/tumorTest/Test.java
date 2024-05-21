package com.tumorTest;

import com.tumorTest.dto.LoginDto;
import com.tumorTest.mapper.UserMapper;
import com.tumorTest.properties.AliOssProperties;
import com.tumorTest.result.Result;
import com.tumorTest.service.UserService;
import com.tumorTest.vo.UserVo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    @Resource
    AliOssProperties aliOssProperties;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    UserService userService;

    @org.junit.Test
    public void test1(){
        Result<UserVo> zhangsan = userService.userLogin(new LoginDto("zhangsan", "123456"));
        System.out.println(zhangsan);
        Object o = redisTemplate.opsForValue().get("login:token" + zhangsan.getData().getToken());
        System.out.println(o);
    }


}
