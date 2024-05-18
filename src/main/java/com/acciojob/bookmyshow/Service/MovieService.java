package com.acciojob.bookmyshow.Service;

import com.acciojob.bookmyshow.Models.Movie;
import com.acciojob.bookmyshow.Repository.MovieRepository;
import com.acciojob.bookmyshow.Requests.AddMovieRequest;
import com.acciojob.bookmyshow.Requests.UpdateMovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService
{
    @Autowired
    private MovieRepository movieRepository;

    public String addMovie(AddMovieRequest addMovieRequest)
    {
        Movie movie=new Movie();
        movie.setMovieName(addMovieRequest.getMovieName());
        movie.setDuration(addMovieRequest.getDuration());
        movie.setLanguage(addMovieRequest.getLanguage());
        movie.setRatings(addMovieRequest.getRatings());
        movie.setReleaseDate(addMovieRequest.getReleaseDate());

        movie=movieRepository.save(movie);
        return "Movie has been added to the DB with movieID: "+movie.getMovieId();
    }

    public String updateMovieAttributes(UpdateMovieRequest movieRequest)
    {
        //get movie entity
        Movie movie=movieRepository.findMovieByMovieName(movieRequest.getMovieName());
        //Update new attributes
        movie.setLanguage(movieRequest.getNewLanguage());
        movie.setRatings(movieRequest.getNewRating());

        //Save to db
        movieRepository.save(movie);
        return "Your movie attributes have been updated in the database.";
    }
}
