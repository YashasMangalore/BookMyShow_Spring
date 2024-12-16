package BookMyShow.Service.Impl;

import BookMyShow.Dto.Request.AddMovieRequest;
import BookMyShow.Dto.Request.UpdateMovieRequest;
import BookMyShow.Exceptions.MovieServiceException;
import BookMyShow.Exceptions.UserServiceException;
import BookMyShow.Models.Movie;
import BookMyShow.Models.Show;
import BookMyShow.Repository.MovieRepository;
import BookMyShow.Repository.ShowRepository;
import BookMyShow.Service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService  {
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;

    @Override
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

    @Override
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

    @Override
    public List<String> theatreListOfMovies( String movieName )
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

    @Override
    public List<String> theatreListOfMovies( String movieName, LocalDate date )
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

    @Override
    public String deleteMovie( String movieName )
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
