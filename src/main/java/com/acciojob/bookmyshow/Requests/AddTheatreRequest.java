package com.acciojob.bookmyshow.Requests;

import lombok.Data;

@Data
public class AddTheatreRequest
{
    private Integer noOfScreens;
    private String name;
    private String address;
}
