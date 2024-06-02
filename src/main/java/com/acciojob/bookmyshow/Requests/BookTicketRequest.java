package com.acciojob.bookmyshow.Requests;

import lombok.Data;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookTicketRequest
{
    private Integer showId;
    private List<String> requestedSeats;
    private Integer userId;
//    private String movieName;
//    private LocalDate showDate;
//    private LocalTime showTime;
//    private Integer theatreId;
}
