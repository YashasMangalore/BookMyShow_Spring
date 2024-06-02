package com.acciojob.bookmyshow.Controller;

import com.acciojob.bookmyshow.Exceptions.TheatreServiceException;
import com.acciojob.bookmyshow.Requests.AddTheatreRequest;
import com.acciojob.bookmyshow.Requests.AddTheatreSeatRequest;
import com.acciojob.bookmyshow.Requests.UpdateTheatreRequest;
import com.acciojob.bookmyshow.Service.TheatreService;
import com.acciojob.bookmyshow.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("theatre")
public class TheatreController
{
    @Autowired
    private TheatreService theatreService;
    @Autowired
    private TicketService ticketService;

    @PostMapping("add")//add theatre to db
    public ResponseEntity<String> addTheatre(@RequestBody AddTheatreRequest theatreRequest) throws TheatreServiceException
    {
        try
        {//now convert this to an entity
            String response = theatreService.addTheatre(theatreRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (TheatreServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update")//update theatre attributes in DB
    public ResponseEntity<String> updateAttributes(@RequestBody UpdateTheatreRequest theatreRequest) throws TheatreServiceException
    {
        try
        {
            String response = theatreService.updateTheatreAttributes(theatreRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (TheatreServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("associate-seats")//associate physical seats with theatre
    public ResponseEntity<String> associateSeats(@RequestBody AddTheatreSeatRequest theatreSeatRequest) throws TheatreServiceException
    {
        try
        {
            String response = theatreService.associateTheatreSeats(theatreSeatRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (TheatreServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("revenues")// revenue of theatre on a particular day
    public ResponseEntity<Double> revenue(@RequestParam String theatreName, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
    {
        Double response= theatreService.revenue(theatreName,date);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("city")//get theatre list from city
    public ResponseEntity<List<String>> theatreList(@RequestParam String city)
    {
        List<String> theatreList=theatreService.theatreListInCity(city);
        return new ResponseEntity<>(theatreList,HttpStatus.OK);
    }

    @GetMapping("movies")//get movies from theatre name
    public ResponseEntity<List<String>> movieList(@RequestParam String theatreName)
    {
        List<String> theatreList=theatreService.movieListInTheatre(theatreName);
        return new ResponseEntity<>(theatreList,HttpStatus.OK);
    }

    @GetMapping("movies-list")//get movies from theatre name and date
    public ResponseEntity<List<String>> movieList(@RequestParam String theatreName, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
    {
        List<String> theatreList=theatreService.movieListInTheatre(theatreName,date);
        return new ResponseEntity<>(theatreList,HttpStatus.OK);
    }

    @DeleteMapping("delete")//delete from db
    public ResponseEntity<String> delete(@RequestParam Integer theatreId) throws TheatreServiceException
    {
        try
        {
            String response = theatreService.delete(theatreId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (TheatreServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}