package com.acciojob.bookmyshow.Requests;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class BookTicketRequest
{
    private Integer showId;
//    private String movieName;
//    private LocalDate showDate;
//    private LocalTime showTime;
    private List<String> requestedSeats;
    private Integer userId;
//    private Integer theatreId;
}
