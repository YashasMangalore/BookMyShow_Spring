package com.acciojob.bookmyshow.Service;

import com.acciojob.bookmyshow.Enums.SeatType;
import com.acciojob.bookmyshow.Models.Show;
import com.acciojob.bookmyshow.Models.ShowSeat;
import com.acciojob.bookmyshow.Models.Ticket;
import com.acciojob.bookmyshow.Models.User;
import com.acciojob.bookmyshow.Repository.ShowRepository;
import com.acciojob.bookmyshow.Repository.ShowSeatRepository;
import com.acciojob.bookmyshow.Repository.TicketRepository;
import com.acciojob.bookmyshow.Repository.UserRepository;
import com.acciojob.bookmyshow.Requests.BookTicketRequest;
import com.acciojob.bookmyshow.Responses.TicketResponse;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public String bookTicket(BookTicketRequest bookTicketRequest)
    {
        //1. find show entity
        Show show=showRepository.findById(bookTicketRequest.getShowId()).get();
        //2 find user entity
        User user=userRepository.findById(bookTicketRequest.getUserId()).get();
        //3 mark as booked and calculate total amount
        Integer totalAmount=0;
        List<ShowSeat> showSeatList=show.getShowSeatList();

        for(ShowSeat showSeat:showSeatList)
        {
            String seatNo=showSeat.getSeatNo();
            if(bookTicketRequest.getRequestedSeats().contains(seatNo))
            {
                showSeat.setIsBooked(Boolean.TRUE);

                if(showSeat.getSeatType().equals(SeatType.CLASSIC))
                {
                    totalAmount=totalAmount+show.getClassicSeatPrice();
                }
                else
                {
                    totalAmount=totalAmount+show.getPremiumSeatPrice();
                }
            }
        }
        //4 create ticket entity and set attributes
        Ticket ticket=Ticket.builder()
                .showTime(show.getShowTime())
                .showDate(show.getShowDate())
                .movieName(show.getMovie().getMovieName())
                .theatreName(show.getTheatre().getName())
                .bookedSeats(bookTicketRequest.getRequestedSeats().toString())
                .totalAmount(totalAmount)
                .show(show)
                .user(user)
                .build();

        //5 save and return
        showSeatRepository.saveAll(showSeatList);
        ticket= ticketRepository.save(ticket);
        return "The ticket has been generated with ticket-id: "+ticket.getTicketId();
    }

    public TicketResponse generateTicket(String ticketId)
    {
        Ticket ticket=ticketRepository.findById(ticketId).get();
        //entity needs to be converted to responses
        TicketResponse ticketResponse=TicketResponse.builder()
                .bookedSeats(ticket.getBookedSeats())
                .movieName(ticket.getMovieName())
                .showDate(ticket.getShowDate())
                .showTime(ticket.getShowTime())
                .theatreName(ticket.getTheatreName())
                .totalAmount(ticket.getTotalAmount())
                .build();

        return ticketResponse;
    }
}