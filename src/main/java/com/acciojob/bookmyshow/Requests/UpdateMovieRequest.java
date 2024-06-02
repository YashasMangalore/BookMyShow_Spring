package com.acciojob.bookmyshow.Requests;

import com.acciojob.bookmyshow.Enums.Languages;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateMovieRequest
{
    private String movieName;

    private Languages newLanguage;
    private Double newRating;
    private Double newDuration;
}
