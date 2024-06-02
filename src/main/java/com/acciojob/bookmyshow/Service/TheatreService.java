package com.acciojob.bookmyshow.Service;

import com.acciojob.bookmyshow.Enums.SeatType;
import com.acciojob.bookmyshow.Exceptions.TheatreServiceException;
import com.acciojob.bookmyshow.Exceptions.UserServiceException;
import com.acciojob.bookmyshow.Models.Show;
import com.acciojob.bookmyshow.Models.Theatre;
import com.acciojob.bookmyshow.Models.TheatreSeat;
import com.acciojob.bookmyshow.Models.Ticket;
import com.acciojob.bookmyshow.Repository.ShowRepository;
import com.acciojob.bookmyshow.Repository.TheatreRepository;
import com.acciojob.bookmyshow.Repository.TheatreSeatRepository;
import com.acciojob.bookmyshow.Repository.TicketRepository;
import com.acciojob.bookmyshow.Requests.AddTheatreRequest;
import com.acciojob.bookmyshow.Requests.AddTheatreSeatRequest;
import com.acciojob.bookmyshow.Requests.UpdateTheatreRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TheatreService
{
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private TheatreSeatRepository theatreSeatRepository;
    @Autowired
    private ShowRepository showRepository;

    public String addTheatre(AddTheatreRequest theatreRequest) throws TheatreServiceException
    {
        try
        {
            Theatre theatre = Theatre.builder().noOfScreens(theatreRequest.getNoOfScreens())
                    .theatreName(theatreRequest.getTheatreName())
                    .city(theatreRequest.getCity())
                    .build();

            theatre = theatreRepository.save(theatre);
            return "Theatre has been saved to the DataBase with theatreId: " + theatre.getTheatreId();
        }
        catch (TheatreServiceException e)
        {
            throw new TheatreServiceException("Failed to add theatre." + e.getMessage());
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public String associateTheatreSeats(AddTheatreSeatRequest theatreSeatRequest) throws TheatreServiceException
    {
        try
        {
            int theatreId = theatreSeatRequest.getTheatreId();
            int noOfClassicSeats = theatreSeatRequest.getNoOfClassicSeats();
            int noOfPremiumSeats = theatreSeatRequest.getNoOfPremiumSeats();
            List<TheatreSeat> theatreSeatList = new ArrayList<>();
            //1 get theatreEntity from DB
            Theatre theatre = theatreRepository.findById(theatreId).get();
            if(theatre==null)
            {
                throw new TheatreServiceException("The theatre is not present in Database to associate with the seats.");
            }
            //2 generate seatNO
            int noOfRowsOfClassicSeats = noOfClassicSeats / 5;
            int noOfSeatsInLastRow = noOfClassicSeats % 5;
            int row;

            for (row = 1; row <= noOfRowsOfClassicSeats; row++) {
                for (int j = 1; j <= 5; j++) {
                    char ch = (char) ('A' + j - 1);
                    String seatNo = "" + row + ch;

                    TheatreSeat theatreSeat = TheatreSeat.builder().seatNo(seatNo)
                            .seatType(SeatType.CLASSIC)
                            .theatre(theatre)//theatre entity is foreign key so set
                            .build();

                    theatreSeatList.add(theatreSeat);
                }
            }
            for (int j = 1; j <= noOfSeatsInLastRow; j++) //for last row
            {
                char ch = (char) ('A' + j - 1);
                String seatNo = "" + row + ch;

                TheatreSeat theatreSeat = TheatreSeat.builder().seatNo(seatNo)
                        .seatType(SeatType.CLASSIC)
                        .theatre(theatre)//theatre entity is foreign key so set
                        .build();

                theatreSeatList.add(theatreSeat);
            }

            //same for premium
            int cur = (noOfSeatsInLastRow > 0) ? row + 1 : row;
            int noOfRowsOfPremiumSeats = noOfPremiumSeats / 5;
            noOfSeatsInLastRow = noOfPremiumSeats % 5;

            for (row = cur; row <= cur + noOfRowsOfPremiumSeats; row++) {
                for (int j = 1; j <= 5; j++) {
                    char ch = (char) ('A' + j - 1);
                    String seatNo = "" + row + ch;

                    TheatreSeat theatreSeat = TheatreSeat.builder().seatNo(seatNo)
                            .seatType(SeatType.PREMIUM)
                            .theatre(theatre)//theatre entity is foreign key so set
                            .build();

                    theatreSeatList.add(theatreSeat);
                }
            }
            for (int j = 1; j <= noOfSeatsInLastRow; j++) //for last row
            {
                char ch = (char) ('A' + j - 1);
                String seatNo = "" + row + ch;

                TheatreSeat theatreSeat = TheatreSeat.builder().seatNo(seatNo)
                        .seatType(SeatType.PREMIUM)
                        .theatre(theatre)//theatre entity is foreign key so set
                        .build();

                theatreSeatList.add(theatreSeat);
            }
            theatre.setTheatreSeatList(theatreSeatList);
            theatreRepository.save(theatre);

            //save all to DB
            theatreSeatRepository.saveAll(theatreSeatList);

            return "The theatre seats has been associated";
        }
        catch (TheatreServiceException e)
        {
            throw new TheatreServiceException("Failed to associate theatre seats." + e.getMessage());
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public Double revenue(String theatreName, LocalDate date)
    {
        List<Ticket> ticketList=ticketRepository.findAll();
        Double totalRevenue=0.00;
        if(ticketList.isEmpty())
        {
            return totalRevenue;
        }
        for(Ticket ticket:ticketList)
        {
            if(ticket.getTheatreName().equals(theatreName) && ticket.getShowDate().equals(date))
            {
                totalRevenue=totalRevenue+ticket.getTotalAmount();
            }
        }
        return totalRevenue;
    }

    public String updateTheatreAttributes(UpdateTheatreRequest theatreRequest)throws TheatreServiceException
    {
        try
        {
            Theatre theatre = theatreRepository.findTheatreByTheatreName(theatreRequest.getName());
            if (theatre == null)
            {
                throw new TheatreServiceException("Theatre not found in the DataBase");
            }
            if (theatreRequest.getNewTheatreName() != null) {
                theatre.setTheatreName(theatreRequest.getNewTheatreName());
            }
            if (theatreRequest.getNewCity() != null) {
                theatre.setCity(theatreRequest.getNewCity());
            }
            if (theatreRequest.getNewNoOfScreens() != null) {
                theatre.setNoOfScreens(theatreRequest.getNewNoOfScreens());
            }

            theatreRepository.save(theatre);
            return "Your theatre attributes have been updated in the database.";
        }
        catch (TheatreServiceException e)
        {
            throw new TheatreServiceException("Failed to update theatre attributes." + e.getMessage());
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public String delete(Integer theatreId)throws TheatreServiceException
    {
        try
        {
            Optional<Theatre> optionalTheatre = theatreRepository.findById(theatreId);
            if (optionalTheatre.isEmpty())
            {
                throw new RuntimeException("Theatre not found with ID: " + theatreId);
            }
            Theatre theatre = optionalTheatre.get();

            // Find all theatre seats associated with the theatre
            List<TheatreSeat> theatreSeatList = theatreSeatRepository.findByTheatreTheatreId(theatreId);
            if (!theatreSeatList.isEmpty()) {
                theatreSeatRepository.deleteAll(theatreSeatList);
            }
            theatreRepository.delete(theatre);

            return "Theatre with theatre ID " + theatreId + " and its seats have been removed from the database.";
        }
        catch (TheatreServiceException e)
        {
            throw new TheatreServiceException("Failed to delete theatre." + e.getMessage());
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public List<String> theatreListInCity(String city)
    {
        List<Theatre> theatreList = theatreRepository.findAll();
        List<String> ans=new ArrayList<>();
        for(Theatre theatre:theatreList)
        {
            if(theatre.getCity().equals(city))
            {
                ans.add(theatre.getTheatreName());
            }
        }
        return ans;
    }

    public List<String> movieListInTheatre(String theatreName)
    {
        List<Show> showList=showRepository.findAll();
        HashSet<String> st=new HashSet<>();
        for(Show show:showList)
        {
            if(show.getTheatre().getTheatreName().equals(theatreName))
            {
                st.add(show.getMovie().getMovieName());
            }
        }
        List<String> ans=new ArrayList<>();
        ans.addAll(st);
        return ans;//or return new ArrayList<>(st);
    }

    public List<String> movieListInTheatre(String theatreName, LocalDate date)
    {
        List<Show> showList=showRepository.findAll();
        List<String> ans=new ArrayList<>();
        for(Show show:showList)
        {
            if(show.getTheatre().getTheatreName().equals(theatreName) && show.getShowDate().equals(date))
            {
                ans.add("Movie name: "+show.getMovie().getMovieName()+" Movie time: "+show.getShowTime());
            }
        }
        return ans;
    }
}
