package com.acciojob.bookmyshow.Controller;

import com.acciojob.bookmyshow.Requests.BookTicketRequest;
import com.acciojob.bookmyshow.Responses.TicketResponse;
import com.acciojob.bookmyshow.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ticket")
public class TicketController
{
    @Autowired
    private TicketService ticketService;
    @PostMapping("bookTicket")
    public String bookTicket(@RequestBody BookTicketRequest bookTicketRequest)
    {
        return ticketService.bookTicket(bookTicketRequest);
    }

    @GetMapping("viewTicket")
    public TicketResponse generateTicket(@RequestParam("ticketId")String ticketId)
    {
        return ticketService.generateTicket(ticketId);
    }
}
