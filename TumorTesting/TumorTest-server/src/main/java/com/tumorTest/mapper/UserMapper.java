package com.tumorTest.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tumorTest.dto.ShowBookingResultDto;
import com.tumorTest.entity.Booking;
import com.tumorTest.entity.User;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据预约id查询预约结果
     * @param bookingId
     * @return
     */
    @Select("select * from consultation where booking_id = #{bookingId}")
    ShowBookingResultDto getByBookingId(Long bookingId);

    /**
     * 根据用户id查询个人信息
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{userId}")
    User getPersonalInformationByUserId(Integer userId);
}
