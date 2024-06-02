package com.acciojob.bookmyshow.Requests;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateShowRequest
{
    private Integer showId;

    private String movieName;
    private LocalDate showDate;
    private LocalTime showTime;
    private Integer classicSeatPrice;
    private Integer premiumSeatPrice;
    private Integer screenNumber;
}
