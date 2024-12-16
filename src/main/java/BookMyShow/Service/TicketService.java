package BookMyShow.Service;

import BookMyShow.Dto.Request.BookTicketRequest;
import BookMyShow.Dto.Response.TicketResponse;

public interface TicketService {
    String bookTicket( BookTicketRequest bookTicketRequest);
    TicketResponse generateTicket( String ticketId);
    String cancelTicket(String ticketId);
}
