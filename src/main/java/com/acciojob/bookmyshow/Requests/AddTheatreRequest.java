package com.acciojob.bookmyshow.Requests;

import lombok.Data;

@Data
public class AddTheatreRequest
{
    private Integer noOfScreens;
    private String theatreName;
    private String city;
}
