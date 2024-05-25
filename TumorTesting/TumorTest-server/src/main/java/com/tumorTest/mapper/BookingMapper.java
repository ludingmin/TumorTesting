package com.tumorTest.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tumorTest.entity.Booking;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookingMapper extends BaseMapper<Booking> {

    @Update("update booking set img_url = #{imgUrl} where booking_id =#{bookId}")
    public boolean updateImgUrlByBookingId(@Param("bookId") String bookId,@Param("imgUrl") String imgUrl);

    @Update("update booking set state = #{state} where booking_id =#{bookId}")
    public boolean updateState(@Param("bookId") String bookId,@Param("state") int state);

}
