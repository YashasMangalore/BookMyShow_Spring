package com.acciojob.bookmyshow.Models;

import com.acciojob.bookmyshow.Enums.Languages;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(unique = true)
    private String movieName;

    @Enumerated(value = EnumType.STRING)//for java to understand
    private Languages language;

    private Double duration;
    private Double ratings;
    private LocalDate releaseDate;
}
