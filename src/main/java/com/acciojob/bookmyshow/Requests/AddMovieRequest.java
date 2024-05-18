package com.acciojob.bookmyshow.Requests;

import com.acciojob.bookmyshow.Enums.Languages;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AddMovieRequest
{
    private String movieName;
    private Languages language;
    private Double duration;
    private Double ratings;
    private LocalDate releaseDate;
}
