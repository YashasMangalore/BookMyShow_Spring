package BookMyShow.Service;

import BookMyShow.Dto.Request.AddMovieRequest;
import BookMyShow.Dto.Request.UpdateMovieRequest;
import BookMyShow.Exceptions.MovieServiceException;

import java.time.LocalDate;
import java.util.List;

public interface MovieService {
    String addMovie( AddMovieRequest addMovieRequest)throws MovieServiceException;
    String updateMovieAttributes( UpdateMovieRequest movieRequest)throws MovieServiceException;
    List<String> theatreListOfMovies( String movieName);
    List<String> theatreListOfMovies(String movieName, LocalDate date);
    String deleteMovie(String movieName);
}
