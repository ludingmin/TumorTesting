package com.tumorTest.vo;

import com.tumorTest.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookHistory {

    public BookHistory (Booking booking){
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.bookingId=booking.getBookingId();
        this.time=booking.getTime().format(date);
        this.doctorName=booking.getDoctorName();
        this.state=booking.getState();
        this.userId=booking.getUserId();
    }
    private Long bookingId;
    private Long userId;
    private String doctorName;
    private String time;
    private Integer state;

}
