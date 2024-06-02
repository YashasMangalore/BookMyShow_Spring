package com.acciojob.bookmyshow.Controller;

import com.acciojob.bookmyshow.Exceptions.TheatreServiceException;
import com.acciojob.bookmyshow.Exceptions.TicketServiceException;
import com.acciojob.bookmyshow.Requests.BookTicketRequest;
import com.acciojob.bookmyshow.Responses.TicketResponse;
import com.acciojob.bookmyshow.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ticket")
public class TicketController
{
    @Autowired
    private TicketService ticketService;
    @PostMapping("book-ticket")//books a ticket
    public ResponseEntity<String> bookTicket(@RequestBody BookTicketRequest bookTicketRequest) throws TicketServiceException
    {
        try
        {
            String response = ticketService.bookTicket(bookTicketRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (TicketServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/view-ticket")
    public ResponseEntity<TicketResponse> generateTicket(@RequestParam("ticketId") String ticketId) throws TicketServiceException
    {
        try
        {
            TicketResponse response = ticketService.generateTicket(ticketId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (TicketServiceException e)
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cancel-ticket")
    public ResponseEntity<String> cancelTicket(@RequestParam("ticketId") String ticketId) throws TicketServiceException
    {
        try
        {
            String response = ticketService.cancelTicket(ticketId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (TicketServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
