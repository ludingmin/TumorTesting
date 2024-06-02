package com.tumorTest.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tumorTest.entity.Booking;
import com.tumorTest.vo.BookingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BookingMapper extends BaseMapper<Booking> {

    @Update("update booking set img_url = #{imgUrl} where booking_id =#{bookId}")
    public boolean updateImgUrlByBookingId(@Param("bookId") String bookId,@Param("imgUrl") String imgUrl);

    @Update("update booking set state = #{state} where booking_id =#{bookId}")
    public boolean updateState(@Param("bookId") String bookId,@Param("state") int state);

    @Select("SELECT user.id,user.name,user.address,user.number,booking.booking_id from user,booking where doctor_name= #{doctorname} AND booking.state=0 AND booking.user_id=user.id AND booking.time BETWEEN #{date1} and #{date2}")
    public List<BookingVo> joinselect(@Param("doctorname") String doctorname , @Param("date1")LocalDateTime date1, @Param("date2") LocalDateTime date2);

    @Select("select booking_id from booking where user_id = #{userId}")
    public Long selectBookingIdIntegerByUserId(Long userId);
}
