package BookMyShow.Service.Impl;

import BookMyShow.Dto.Request.BookTicketRequest;
import BookMyShow.Dto.Response.TicketResponse;
import BookMyShow.Enums.SEAT_TYPE;
import BookMyShow.Exceptions.TicketServiceException;
import BookMyShow.Models.Show;
import BookMyShow.Models.ShowSeat;
import BookMyShow.Models.Ticket;
import BookMyShow.Models.User;
import BookMyShow.Repository.ShowRepository;
import BookMyShow.Repository.ShowSeatRepository;
import BookMyShow.Repository.TicketRepository;
import BookMyShow.Repository.UserRepository;
import BookMyShow.Service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Override
    public String bookTicket( BookTicketRequest bookTicketRequest)
    {
        try
        {
            //1. find show entity
            Show show = showRepository.findById(bookTicketRequest.getShowId())
                    .orElseThrow(() -> new TicketServiceException("Show not found with ID: " + bookTicketRequest.getShowId()));
            //2 find user entity
            User user = userRepository.findById(bookTicketRequest.getUserId())
                    .orElseThrow(() -> new TicketServiceException("User not found with ID: " + bookTicketRequest.getUserId()));
            //3 mark as booked and calculate total amount
            int totalAmount = 0;
            List<ShowSeat> showSeatList = show.getShowSeatList();

            for (ShowSeat showSeat : showSeatList)
            {
                String seatNo = showSeat.getSeatNo();
                if (bookTicketRequest.getRequestedSeats().contains(seatNo))
                {
                    showSeat.setIsBooked(Boolean.TRUE);

                    if (showSeat.getSeatType().equals(SEAT_TYPE.CLASSIC))
                    {
                        totalAmount = totalAmount + show.getClassicSeatPrice();
                    }
                    else
                    {
                        totalAmount = totalAmount + show.getPremiumSeatPrice();
                    }
                }
            }
            //4 create ticket entity and set attributes
            Ticket ticket = Ticket.builder()
                    .showTime(show.getShowTime())
                    .showDate(show.getShowDate())
                    .movieName(show.getMovie().getMovieName())
                    .theatreName(show.getTheatre().getTheatreName())
                    .screenNumber(show.getScreenNumber())
                    .bookedSeats(bookTicketRequest.getRequestedSeats().toString())
                    .totalAmount(totalAmount)
                    .show(show)
                    .user(user)
                    .build();

            //5 save and return
            showSeatRepository.saveAll(showSeatList);
            ticket = ticketRepository.save(ticket);
            return "The ticket has been generated with ticket-id: " + ticket.getTicketId();
        }
        catch(TicketServiceException e)
        {
            throw new TicketServiceException("Failed to add theatre." + e.getMessage());
        }
    }

    @Override
    public TicketResponse generateTicket( String ticketId)
    {
        try
        {
            Ticket ticket = ticketRepository.findById(ticketId)
                    .orElseThrow(() -> new TicketServiceException("Ticket not found with ID: " + ticketId));
            //entity needs to be converted to responses

            return TicketResponse.builder()
                    .bookedSeats(ticket.getBookedSeats())
                    .movieName(ticket.getMovieName())
                    .showDate(ticket.getShowDate())
                    .showTime(ticket.getShowTime())
                    .theatreName(ticket.getTheatreName())
                    .screenNumber(ticket.getScreenNumber())
                    .totalAmount(ticket.getTotalAmount())
                    .build();
        }
        catch(TicketServiceException e)
        {
            throw new TicketServiceException("Failed to add theatre." + e.getMessage());
        }
    }

    @Override
    public String cancelTicket(String ticketId)
    {
        try
        {
            Optional<Ticket> ticketOpt = ticketRepository.findById(ticketId);
            if (ticketOpt.isEmpty())
            {
                throw new TicketServiceException("Ticket not found");
            }
            Ticket ticket = ticketOpt.get();

            // Update seat availability
            List<String> bookedSeats = List.of(ticket.getBookedSeats().split(", "));
            List<ShowSeat> showSeatList = ticket.getShow().getShowSeatList();
            for (ShowSeat showSeat : showSeatList)
            {
                if (bookedSeats.contains(showSeat.getSeatNo()))
                {
                    showSeat.setIsBooked(Boolean.FALSE);
                }
            }
            // Save the updated seats
            showSeatRepository.saveAll(showSeatList);
            // Delete the ticket
            ticketRepository.delete(ticket);
            return "The ticket with ticket-ID: " + ticketId + " has been successfully canceled";
        }
        catch(TicketServiceException e)
        {
            throw new TicketServiceException("Failed to add theatre." + e.getMessage());
        }
    }
}
