package com.acciojob.bookmyshow.Service;

import com.acciojob.bookmyshow.Enums.SeatType;
import com.acciojob.bookmyshow.Exceptions.TicketServiceException;
import com.acciojob.bookmyshow.Models.*;
import com.acciojob.bookmyshow.Repository.*;
import com.acciojob.bookmyshow.Requests.BookTicketRequest;
import com.acciojob.bookmyshow.Responses.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService
{
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;

    public String bookTicket(BookTicketRequest bookTicketRequest) throws Exception
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

                    if (showSeat.getSeatType().equals(SeatType.CLASSIC))
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

    public TicketResponse generateTicket(String ticketId) throws Exception
    {
        try
        {
            Ticket ticket = ticketRepository.findById(ticketId)
                    .orElseThrow(() -> new TicketServiceException("Ticket not found with ID: " + ticketId));
            //entity needs to be converted to responses
            TicketResponse ticketResponse = TicketResponse.builder()
                    .bookedSeats(ticket.getBookedSeats())
                    .movieName(ticket.getMovieName())
                    .showDate(ticket.getShowDate())
                    .showTime(ticket.getShowTime())
                    .theatreName(ticket.getTheatreName())
                    .screenNumber(ticket.getScreenNumber())
                    .totalAmount(ticket.getTotalAmount())
                    .build();

            return ticketResponse;
        }
        catch(TicketServiceException e)
        {
            throw new TicketServiceException("Failed to add theatre." + e.getMessage());
        }
    }

    public String cancelTicket(String ticketId)throws Exception
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