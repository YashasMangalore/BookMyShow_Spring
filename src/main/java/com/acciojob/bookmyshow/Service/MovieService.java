package com.acciojob.bookmyshow.Service;

import com.acciojob.bookmyshow.Exceptions.MovieServiceException;
import com.acciojob.bookmyshow.Exceptions.UserServiceException;
import com.acciojob.bookmyshow.Models.Movie;
import com.acciojob.bookmyshow.Models.Show;
import com.acciojob.bookmyshow.Repository.MovieRepository;
import com.acciojob.bookmyshow.Repository.ShowRepository;
import com.acciojob.bookmyshow.Requests.AddMovieRequest;
import com.acciojob.bookmyshow.Requests.UpdateMovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService
{
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ShowRepository showRepository;

    public String addMovie(AddMovieRequest addMovieRequest) throws MovieServiceException
    {
        try
        {
            Movie movie = new Movie();
            movie.setMovieName(addMovieRequest.getMovieName());
            movie.setDuration(addMovieRequest.getDuration());
            movie.setLanguage(addMovieRequest.getLanguage());
            movie.setRatings(addMovieRequest.getRatings());
            movie.setReleaseDate(addMovieRequest.getReleaseDate());

            movie = movieRepository.save(movie);
            return "Movie has been added to the DB with movieID: " + movie.getMovieId();
        }
        catch (MovieServiceException e)
        {
            // Handle any exception that might occur during database access or processing
            throw new MovieServiceException("Movie could not be added to the DataBase. Try again!", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public String updateMovieAttributes(UpdateMovieRequest movieRequest)throws MovieServiceException
    {
        try
        {
            //get movie entity
            Movie movie = movieRepository.findMovieByMovieName(movieRequest.getMovieName());
            if (movie == null)
            {
                throw new MovieServiceException("Movie not found");
            }
            //Update new attributes
            if (movieRequest.getNewLanguage() != null)
            {
                movie.setLanguage(movieRequest.getNewLanguage());
            }
            if (movieRequest.getNewRating() != null)
            {
                movie.setRatings(movieRequest.getNewRating());
            }
            if (movieRequest.getNewDuration() != null)
            {
                movie.setDuration(movieRequest.getNewDuration());
            }
            //Save to db
            movieRepository.save(movie);
            return "Your movie attributes have been updated in the database.";
        }
        catch (MovieServiceException e)
        {
            // Handle any exception that might occur during database access or processing
            throw new MovieServiceException("Movie cannot be added to the DataBase.", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public List<String> theatreListOfMovies(String movieName)
    {
        List<String> ans = new ArrayList<>();
        List<Show> showList = showRepository.findAll();
        for (Show show : showList)
        {
            if (show.getMovie().getMovieName().equals(movieName))
            {
                ans.add(show.getTheatre().getTheatreName());
            }
        }
        return ans;
    }

    public List<String> theatreListOfMovies(String movieName, LocalDate date)
    {
        List<String> ans = new ArrayList<>();

        List<Show> showList = showRepository.findAll();
        for (Show show : showList)
        {
            if (show.getMovie().getMovieName().equals(movieName) && show.getShowDate().equals(date))
            {
                ans.add(show.getTheatre().getTheatreName());
            }
        }
        return ans;
    }

    public String deleteMovie(String movieName) throws MovieServiceException
    {
        try
        {
            Movie movie = movieRepository.findMovieByMovieName(movieName);
            if (movie == null)
            {
                throw new MovieServiceException("Movie not found");
            }
            movieRepository.delete(movie);
            return "The movie " + movieName + " has been deleted from the database";
        }
        catch (MovieServiceException e)
        {
            // Handle any exception that might occur during database access or processing
            throw new MovieServiceException("An error occurred while fetching the movie: " + movieName, e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }
}