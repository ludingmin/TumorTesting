package com.tumorTest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.tumorTest.dto.CreateUseDto;
import com.tumorTest.dto.LoginDto;
import com.tumorTest.dto.UserDto;
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
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    @Resource
    AliOssProperties aliOssProperties;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @org.junit.Test
    public void test1(){
        Result<UserVo> zhangsan = userService.userLogin(new LoginDto("zhangsan", "123456"));
        System.out.println(zhangsan);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries("login:token:" + zhangsan.getData().getToken());
        UserDto userDto = BeanUtil.fillBeanWithMap(entries, new UserDto(), false);
        System.out.println(entries);
        System.out.println(userDto);
    }

    @org.junit.Test
    public void test2(){
        CreateUseDto createUseDto = new CreateUseDto("lisi8", "13928791132", "123456", 15, "随便", "511487");
        System.out.println(userService.createUser(createUseDto));

    }


}
