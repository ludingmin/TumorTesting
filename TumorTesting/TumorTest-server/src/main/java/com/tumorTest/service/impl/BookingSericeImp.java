package com.tumorTest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.tumorTest.constant.RedisConstant;
import com.tumorTest.dto.UserDto;
import com.tumorTest.entity.Booking;
import com.tumorTest.mapper.BookingMapper;
import com.tumorTest.result.Result;
import com.tumorTest.service.BookingService;
import com.tumorTest.vo.BookingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.tumorTest.constant.RedisConstant.KEY_BOOKING_TOTAL;
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
    public  synchronized Result bookingadd(String doctor) {
        //获取当前日期
        LocalDateTime dateTime = LocalDateTime.now();

        //获取当前日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = dateTime.format(formatter);

//        获取当前时间
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format1 = dateTime.format(formatter1);
        //获取redis中存放的日期值
        Set<Object> members = redisTemplate.opsForSet().members(format);
        System.out.println(members);
        //如果今日还未开始预约则更新redis的总数
        if (members==null || members.isEmpty()){
            redisTemplate.opsForValue().set(RedisConstant.KEY_BOOKING_TOTAL,total);
            System.out.println(1);
        }
        //时间
        LocalDateTime date2 = LocalDateTime.parse(format1, formatter1);

        //获取用户id
        UserDto user = getUser();
        Long id = user.getId();

        //查询今日是否已预约
        Long add = redisTemplate.opsForSet().add(format, id);
        if (add==0)
            return Result.error("今天已预约");
        Booking booking = new Booking();
        //封装属性
        booking.setUserId(id);
        booking.setTime(date2);
        booking.setDoctorName(doctor);
        //设置预约状态
        booking.setState(0);
        int insert = bookingMapper.insert(booking);
        //添加数据库失败或者今日预约则失败
        if (insert==0) {
            redisTemplate.opsForSet().remove(format,id);
            return Result.error("预约失败");

        }
        Long bookingId = booking.getBookingId();
        System.out.println(bookingId);
        redisTemplate.opsForSet().add(RedisConstant.KEY_BOOKING_BOOKINGID,bookingId);
        //更新redis
        Integer total1 = (Integer) redisTemplate.opsForValue().get(RedisConstant.KEY_BOOKING_TOTAL);
        total1--;
        //缓存
        redisTemplate.opsForValue().set(RedisConstant.KEY_BOOKING_TOTAL,total1);
        //将已预约的用户添加到redis,设置key的存活时间
        redisTemplate.opsForSet().add(format,id);
        redisTemplate.expire(format,24, TimeUnit.HOURS);
        return Result.success("创建成功");
    }



    //取消预约
    public Result bookingdelete(){

        //获取该线程的用户信息
       UserDto user = getUser();
       //获取当前用户id
        Long id = user.getId();
        //获取当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        //获取当前日期
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = dateTime.format(date);
        //0点
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        //转化为localdatetime类型
        QueryWrapper<Booking> bqw = new QueryWrapper<>();
        //查询今日该用户的预约信息
        bqw.eq("user_id",id).eq("state",0).between("time",startOfDay,dateTime);
        Booking booking = bookingMapper.selectOne(bqw);
        //获取预约单号
        Long bookingId = booking.getBookingId();
        //将用户的状态修改取消预约的状态
        booking.setState(-1);
        //修改数据库里面的该用户的数据
        int i = bookingMapper.updateById(booking);
        if(i==0) {
            return Result.error("取消失败");
        }
        //将预约人数加一
        Integer total1 = (Integer) redisTemplate.opsForValue().get("total");
        total1++;
        //修改redis中的值
        redisTemplate.opsForValue().set(RedisConstant.KEY_BOOKING_TOTAL,total1);
        //将该用户的信息再redis中移除
        redisTemplate.opsForSet().remove(format,id);
        //
        redisTemplate.opsForSet().remove(RedisConstant.KEY_BOOKING_BOOKINGID,bookingId);
        return Result.success("取消成功");
    }


//    用户查询自己的历史预约信息
    @Override
    public Result<List<Booking>> bookingselect1() {
        //获取该用户的信息
        UserDto user = getUser();
        //获取用户名
        Long id = user.getId();
        //进行查询
        QueryWrapper<Booking> bookingQueryWrapper = new QueryWrapper<>();
        bookingQueryWrapper.eq("user_id",id);
        List<Booking> bookings = bookingMapper.selectList(bookingQueryWrapper);
        return Result.success(bookings);
    }


    //医生查看自己的历史被预约信息
    @Override
    public Result<List<Booking>> bookingselect2() {
        UserDto user = getUser();
        String name1 = user.getName();
        QueryWrapper<Booking> bookingQueryWrapper = new QueryWrapper<>();
        bookingQueryWrapper.eq("doctor_name",name1).eq("state",0);
        List<Booking> bookings = bookingMapper.selectList(bookingQueryWrapper);
        return Result.success(bookings);

    }



//    查看当前预约人数的接口
    @Override
    public Result<Integer> current() {
        Integer n = (Integer) redisTemplate.opsForValue().get(RedisConstant.KEY_BOOKING_TOTAL);
        Integer renshu=total-n;
        return Result.success(n);

    }


    //医生查看预约自己的用户信息信息
    @Override
    public Result<List<BookingVo>> doctorbooking() {
        //获取医生的用户名
        UserDto user = getUser();
        String doctorname = user.getName();
        //获取当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        //获取当日0点的时间
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        //查询
        List<BookingVo> joinselect = bookingMapper.joinselect(doctorname,startOfDay,dateTime);
        return Result.success(joinselect);

    }


}
