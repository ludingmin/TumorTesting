package com.tumorTest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tumorTest.dto.CreateBookingDto;
import com.tumorTest.dto.UserDto;
import com.tumorTest.entity.Booking;
import com.tumorTest.mapper.BookingMapper;
import com.tumorTest.result.Result;
import com.tumorTest.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.tumorTest.context.BaseContext.getUser;

@Service
public class BookingSericeImp extends ServiceImpl<BookingMapper, Booking> implements BookingService {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    BookingMapper bookingMapper;



    //设置每天最大的预约人数
    public  static Integer total=50;


    //使用关键字进行修饰
    @Override
    public  synchronized Result bookingadd(CreateBookingDto createBookingDto) {

        //获取当前日期
        LocalDateTime dateTime = LocalDateTime.now();
        //获取当前日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = dateTime.format(formatter);
//        获取当前时间
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format1 = dateTime.format(formatter1);

        //获取redis中存放的日期值
        String data = (String) redisTemplate.opsForValue().get("data");
        //如果redis存放的日期值为空或者小于当前日期值，则更新redis
        if (data==null || format.compareTo(data)>0){
            redisTemplate.opsForValue().set("data",format);
            redisTemplate.opsForValue().set("total",total);

        }
//        createBookingDto对应的属性值赋给booking.class
        Booking booking = BeanUtil.copyProperties(createBookingDto, Booking.class);


        System.out.println(format);
        System.out.println(format1);

        //日期
        LocalDate date1 = LocalDate.parse(format, formatter);
        //时间
        LocalDateTime date2 = LocalDateTime.parse(format1, formatter1);

        //封装到类中
        booking.setDate(date1);
        booking.setTime(date2);
        booking.setState(1);

        //获取用户名以及日期
        String name = booking.getName();
        LocalDate date = booking.getDate();

        //查询今日是否已预约
        String b = (String) redisTemplate.opsForValue().get(name+format);
//        System.out.println(b);

        if ( "ture".equals(b))
            return Result.success("预约失败");
        int insert = bookingMapper.insert(booking);
        //添加数据库失败或者今日预约则失败
        if (insert ==0)
            return Result.success("预约失败");
        //更新redis
        Integer total1 = (Integer) redisTemplate.opsForValue().get("total");
        total1--;
        //缓存
        redisTemplate.opsForValue().set("total",total1);
        String name1 = booking.getName();
        //将已预约的用户添加到redis,设置key的存活时间
        redisTemplate.opsForValue().set(name1+format,"ture");
        redisTemplate.expire(name1+format,24, TimeUnit.HOURS);
        return Result.success("创建成功");
    }




    //取消预约
    public Result bookingdelete(String name){

        //获取该线程的用户信息
//        UserDto user = getUser();
        //获取当前日期
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = dateTime.format(formatter);
        LocalDate date1 = LocalDate.parse(format, formatter);


        //获取用户名
//        String name = user.getName();
        QueryWrapper<Booking> bqw = new QueryWrapper<>();

        //查询是否已预约
        String o = (String) redisTemplate.opsForValue().get(name + date1);
        //如果还未预约则提示
        if (!("ture".equals(o)))
            return Result.success("对不起，你今日还未预约");

        //查询今日该用户的预约信息
        bqw.eq("name",name).eq("date",date1).eq("state",1);
        Booking booking = bookingMapper.selectOne(bqw);
        //将用户的状态修改取消预约的状态
        booking.setState(0);

        //修改数据库里面的该用户的数据
        int i = bookingMapper.updateById(booking);
        if(i==0)
            return Result.success("取消失败");
        //删除redis缓存
        redisTemplate.delete(name+date1);
        //将预约人数加一
        Integer total1 = (Integer) redisTemplate.opsForValue().get("total");
        total1++;
        redisTemplate.opsForValue().set("total",total1);
        return Result.success("取消成功");
    }


//    用户查询自己的预约信息
    @Override
    public Result<List<Booking>> bookingselect1(String name) {
        //获取该用户的信息
//        UserDto user = getUser();
        //获取用户名
//        String name = user.getName();
        //进行查询
        QueryWrapper<Booking> bookingQueryWrapper = new QueryWrapper<>();
        bookingQueryWrapper.eq("name",name);
        List<Booking> bookings = bookingMapper.selectList(bookingQueryWrapper);
        return Result.success(bookings);

    }

    @Override
    public Result<List<Booking>> bookingselect2(String name) {
        QueryWrapper<Booking> bookingQueryWrapper = new QueryWrapper<>();
        bookingQueryWrapper.eq("doctor_name",name);
        List<Booking> bookings = bookingMapper.selectList(bookingQueryWrapper);
        return Result.success(bookings);

    }


}
