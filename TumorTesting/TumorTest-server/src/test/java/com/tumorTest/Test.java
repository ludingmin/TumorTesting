package com.tumorTest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.MD5;
import com.tumorTest.dto.LoginDto;
import com.tumorTest.dto.UserDto;
import com.tumorTest.entity.Booking;
import com.tumorTest.mapper.BookingMapper;
import com.tumorTest.mapper.UserMapper;
import com.tumorTest.properties.AliOssProperties;
import com.tumorTest.result.Result;
import com.tumorTest.service.BookingService;
import com.tumorTest.service.UserService;
import com.tumorTest.vo.BookingVo;
import com.tumorTest.vo.UserVo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
    @Resource
    AliOssProperties aliOssProperties;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @Autowired
    BookingMapper bookingMapper;

    @Autowired
    BookingService bookingService;

    @org.junit.Test
    public void test1() {
        Result<UserVo> zhangsan = userService.userLogin(new LoginDto("zhangsan", "123456"));
        System.out.println(zhangsan);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries("login:token:" + zhangsan.getData().getToken());
        UserDto userDto = BeanUtil.fillBeanWithMap(entries, new UserDto(), false);
        System.out.println(entries);
        System.out.println(userDto);
    }

    @org.junit.Test
    public void test2() {
        System.out.println(MD5.create().digestHex("123456"));
    }

    @org.junit.Test
    public void test3() {
        try {
            // 创建 ProcessBuilder 对象，指定要执行的命令（Python 解释器路径和要执行的 Python 文件路径）
            ProcessBuilder pb = new ProcessBuilder("python",
                    "C://Users//Administrator//Desktop//Tumor//TumorTesting//model//start.py");

            // 启动进程
            Process process = pb.start();

            // 获取进程的输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // 读取并打印 Python 脚本的输出
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = stderr.readLine()) != null)
            {
                System.out.println(line);
            }

            // 等待进程执行完成
            int exitCode = process.waitFor();
            System.out.println("Python script execution completed with exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    @org.junit.Test
    public void test4(){
        redisTemplate.opsForSet().add("sys","app");
        redisTemplate.opsForSet().add("sys","a1");
        Set<Object> sys = redisTemplate.opsForSet().members("sys");
        System.out.println(sys);
    }



    @org.junit.Test
    public  void test5(){
        String doctorname="lisi";
        Result<List<BookingVo>> doctorbooking = bookingService.doctorbooking();
        System.out.println(doctorbooking);
    }



    @org.junit.Test
    public  void test6(){
        String doctorname="lisi";
        Result<List<Booking>> listResult = bookingService.bookingselect2(doctorname);
        System.out.println(listResult);

    }

}
