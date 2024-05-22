package com.acciojob.bookmyshow.Requests;

import lombok.Data;

@Data
public class AddTheatreSeatRequest //DTO's are custom classes helping in taking info from clients
{
    private Integer theatreId;
    private Integer noOfClassicSeats;
    private Integer noOfPremiumSeats;
}
