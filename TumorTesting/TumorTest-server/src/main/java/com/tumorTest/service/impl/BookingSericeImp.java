package com.tumorTest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tumorTest.entity.Booking;
import com.tumorTest.mapper.BookingMapper;
import com.tumorTest.service.BookingService;
import org.springframework.stereotype.Service;

@Service
public class BookingSericeImp extends ServiceImpl<BookingMapper, Booking> implements BookingService {
}
