package com.acciojob.bookmyshow.Requests;

import com.acciojob.bookmyshow.Enums.Languages;
import lombok.Data;

@Data
public class UpdateMovieRequest
{
    private String movieName;
    private Languages newLanguage;
    private Double newRating;
}
