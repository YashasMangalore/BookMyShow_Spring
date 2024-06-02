package com.acciojob.bookmyshow.Controller;

import com.acciojob.bookmyshow.Exceptions.ShowServiceException;
import com.acciojob.bookmyshow.Requests.AddShowRequest;
import com.acciojob.bookmyshow.Requests.UpdateShowRequest;
import com.acciojob.bookmyshow.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("show")
public class ShowController
{
    @Autowired
    private ShowService showService;

    @PostMapping("add")//adds show to DB
    public ResponseEntity<String> addShow(@RequestBody AddShowRequest addShowRequest)throws ShowServiceException
    {
        try
        {
            String response = showService.addShow(addShowRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(ShowServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update")//updates show in DB
    public ResponseEntity<String> updateShow(@RequestBody UpdateShowRequest updateShowRequest) throws ShowServiceException
    {
        try
        {
            String response = showService.updateShow(updateShowRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(ShowServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("seats-remaining")//shows remaining unbooked seats
    public ResponseEntity<List<String>> seatsRemaining(@RequestParam Integer showId) throws ShowServiceException
    {
        try
        {
            List<String> response = showService.seatsRemaining(showId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ShowServiceException e)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("theatres")//get list of theatres using movie name
    public ResponseEntity<List<String>> theatreList(@RequestParam String movieName) throws ShowServiceException
    {
        try
        {
            List<String> response = showService.theatreListOfMovie(movieName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ShowServiceException e)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("theatre-list")//get list of theatres using movie name and date
    public ResponseEntity<List<String>> theatreList(@RequestParam String movieName,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws ShowServiceException
    {
        try
        {
            List<String> response = showService.theatreListOfMovie(movieName, date);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ShowServiceException e)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("city-theatres-list")//get list of theatres using movie name and city
    public ResponseEntity<List<String>> theatreList(@RequestParam String movieName,@RequestParam String city) throws ShowServiceException
    {
        try
        {
            List<String> response = showService.theatreListOfMovieInCity(movieName, city);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ShowServiceException e)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("date-city-theatres-list")//get list of theatres using movie name, city and date
    public ResponseEntity<List<String>> theatreList(@RequestParam String movieName,@RequestParam String city,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)throws ShowServiceException
    {
        try
        {
            List<String> response = showService.theatreListOfMovieInCityDate(movieName, city, date);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ShowServiceException e)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("collection-date")// collection of a movie on a particular day
    public ResponseEntity<Double> revenue(@RequestParam String movieName, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
    {
        Double response=showService.collection(movieName,date);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("total-collection")// total collection of a movie
    public ResponseEntity<Double> revenue(@RequestParam String movieName)
    {
        Double response=showService.collection(movieName);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("show-id")//get show id
    public ResponseEntity<String> getShowID(@RequestParam String movieName,@RequestParam String city,@RequestParam String theatreName,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws ShowServiceException
    {
        try
        {
            String response = showService.getShowID(movieName, city, theatreName, date);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ShowServiceException e)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete")//delete a show from DB
    public ResponseEntity<String> deleteShow(@RequestParam Integer showId) throws ShowServiceException
    {
        try
        {
            String response = showService.deleteShow(showId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ShowServiceException e)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}