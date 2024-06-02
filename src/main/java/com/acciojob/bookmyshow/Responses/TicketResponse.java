package com.acciojob.bookmyshow.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse
{
    private String bookedSeats;
    private String movieName;
    private String theatreName;
    private Integer screenNumber;
    private LocalDate showDate;
    private LocalTime showTime;
    private Integer totalAmount;
}
