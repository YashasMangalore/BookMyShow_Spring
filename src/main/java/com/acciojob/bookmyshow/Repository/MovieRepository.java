package com.acciojob.bookmyshow.Repository;

import com.acciojob.bookmyshow.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer>
{
    Movie findMovieByMovieName(String movieName);
}
