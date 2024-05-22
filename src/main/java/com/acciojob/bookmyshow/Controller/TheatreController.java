package com.acciojob.bookmyshow.Controller;

import com.acciojob.bookmyshow.Requests.AddTheatreRequest;
import com.acciojob.bookmyshow.Requests.AddTheatreSeatRequest;
import com.acciojob.bookmyshow.Service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("theatre")
public class TheatreController
{
    @Autowired
    private TheatreService theatreService;

    @PostMapping("add")
    public ResponseEntity addTheatre(@RequestBody AddTheatreRequest theatreRequest)
    {
        //now convert this to an entity
        String response= theatreService.addTheatre(theatreRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("associateSeats")
    public ResponseEntity associateSeats(@RequestBody AddTheatreSeatRequest theatreSeatRequest)
    {
        String response= theatreService.associateTheatreSeats(theatreSeatRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}