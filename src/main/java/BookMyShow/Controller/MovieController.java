package BookMyShow.Controller;

import BookMyShow.Dto.Request.AddMovieRequest;
import BookMyShow.Dto.Request.UpdateMovieRequest;
import BookMyShow.Exceptions.MovieServiceException;
import BookMyShow.Service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/add")//movie adding to DB
    public ResponseEntity<String> addMovie( @RequestBody AddMovieRequest addMovieRequest) throws MovieServiceException
    {
        try
        {
            String response = movieService.addMovie(addMovieRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (MovieServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")//movie updating in DB
    public ResponseEntity<String> updateMovieAttributes(@RequestBody UpdateMovieRequest updateMovieRequest)throws MovieServiceException
    {
        try
        {
            String response = movieService.updateMovieAttributes(updateMovieRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (MovieServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/theatres")//theatre lost from movieName
    public ResponseEntity<List<String>> theatreListOfMovies( @RequestParam String movieName)
    {
        List<String> theatreList = movieService.theatreListOfMovies(movieName);
        return new ResponseEntity<>(theatreList, HttpStatus.OK);

    }

    @GetMapping("/date")//theatre lost from movieName and date
    public ResponseEntity<List<String>> theatreListOfMoviesOnDay(@RequestParam String movieName, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
    {
        List<String> theatreList=movieService.theatreListOfMovies(movieName,date);
        return new ResponseEntity<>(theatreList,HttpStatus.OK);
    }

    @DeleteMapping("/delete")//movie deleting from DB
    public ResponseEntity<String> deleteMovie(@RequestParam String movieName)throws MovieServiceException
    {
        try
        {
            String response = movieService.deleteMovie(movieName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (MovieServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
