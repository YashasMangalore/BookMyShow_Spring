package com.acciojob.bookmyshow.Controller;

import com.acciojob.bookmyshow.Models.Movie;
import com.acciojob.bookmyshow.Requests.AddMovieRequest;
import com.acciojob.bookmyshow.Requests.UpdateMovieRequest;
import com.acciojob.bookmyshow.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movie")
public class MovieController
{
    @Autowired
    private MovieService movieService;

    @PostMapping("add")
    public ResponseEntity addMovie(@RequestBody AddMovieRequest addMovieRequest)
    {
        String response= movieService.addMovie(addMovieRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity updateMovieAttributes(@RequestBody UpdateMovieRequest updateMovieRequest)
    {
        String response=movieService.updateMovieAttributes(updateMovieRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
