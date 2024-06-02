package com.acciojob.bookmyshow.Requests;

import lombok.Data;

@Data
public class UpdateTheatreRequest
{
    private String name;

    private String newTheatreName;
    private Integer newNoOfScreens;
    private String newCity;
}
