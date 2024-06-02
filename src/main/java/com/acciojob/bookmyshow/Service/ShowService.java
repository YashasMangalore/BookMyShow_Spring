package com.acciojob.bookmyshow.Service;

import com.acciojob.bookmyshow.Enums.SeatType;
import com.acciojob.bookmyshow.Exceptions.ShowServiceException;
import com.acciojob.bookmyshow.Exceptions.UserServiceException;
import com.acciojob.bookmyshow.Models.*;
import com.acciojob.bookmyshow.Repository.*;
import com.acciojob.bookmyshow.Requests.AddShowRequest;
import com.acciojob.bookmyshow.Requests.UpdateShowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService
{
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ShowSeatRepository showSeatRepository;
    @Autowired
    private TicketRepository ticketRepository;

    public String addShow(AddShowRequest showRequest) throws ShowServiceException
    {
        try
        {
            Movie movie = movieRepository.findMovieByMovieName(showRequest.getMovieName());
            if (movie == null)
            {
                throw new ShowServiceException("Movie with name " + showRequest.getMovieName() + " not found.");
            }

            Optional<Theatre> theatreOptional = theatreRepository.findById(showRequest.getTheatreId());
            if (theatreOptional.isEmpty())
            {
                throw new ShowServiceException("Theatre with ID " + showRequest.getTheatreId() + " not found.");
            }
            Theatre theatre = theatreOptional.get();

            Show show = Show.builder()
                    .showDate(showRequest.getShowDate())
                    .showTime(showRequest.getShowTime())
                    .classicSeatPrice(showRequest.getClassicSeatPrice())
                    .premiumSeatPrice(showRequest.getPremiumSeatPrice())
                    .screenNumber(showRequest.getScreenNumber())
                    .movie(movie)
                    .theatre(theatre)
                    .build();
            show = showRepository.save(show);

            //associate the corresponding show seats along with it
            List<TheatreSeat> theatreSeatList = theatre.getTheatreSeatList();
            List<ShowSeat> showSeatList = new ArrayList<>();

            for (TheatreSeat theatreSeat : theatreSeatList)
            {
                ShowSeat showSeat = ShowSeat.builder()
                        .seatNo(theatreSeat.getSeatNo())
                        .seatType(theatreSeat.getSeatType())
                        .isBooked(Boolean.FALSE)
                        .show(show)
                        .build();

                showSeatList.add(showSeat);
            }

            show.setShowSeatList(showSeatList);//setting bidirectional mapping

            showSeatRepository.saveAll(showSeatList);
            return "The show has been saved to the DataBase with showId: " + show.getShowId();
        }
        catch (ShowServiceException e)
        {
            throw new ShowServiceException("An error occurred while adding the show", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public String updateShow(UpdateShowRequest showRequest) throws ShowServiceException
    {
         try
         {
            Optional<Show> optionalShow = showRepository.findById(showRequest.getShowId());
            if (optionalShow.isEmpty())
            {
                throw new ShowServiceException("Show not found with ID: " + showRequest.getShowId());
            }
            Show show = optionalShow.get();
            Movie movie = movieRepository.findMovieByMovieName(showRequest.getMovieName());

            // Update the show attributes only if they are provided
            if (movie != null)
            {
                show.setMovie(movie);
            }
            if (showRequest.getShowDate() != null)
            {
                show.setShowDate(showRequest.getShowDate());
            }
            if (showRequest.getShowTime() != null)
            {
                show.setShowTime(showRequest.getShowTime());
            }
            if (showRequest.getPremiumSeatPrice() != null)
            {
                show.setPremiumSeatPrice(showRequest.getPremiumSeatPrice());
            }
            if (showRequest.getClassicSeatPrice() != null)
            {
                show.setClassicSeatPrice(showRequest.getClassicSeatPrice());
            }
            if (showRequest.getScreenNumber() != null)
            {
                show.setScreenNumber(showRequest.getScreenNumber());
            }

            showRepository.save(show);
            return "The show has been updated";
        }
        catch (ShowServiceException e)
        {
            throw new ShowServiceException("An error occurred while updating the show", e);
        }
         catch (Exception e)
         {
             throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
         }
    }

    public List<String> seatsRemaining(Integer showId)throws ShowServiceException
    {
        try
        {
            List<String> ans = new ArrayList<>();
            Optional<Show> showOptional = showRepository.findById(showId);
            if (showOptional.isEmpty())
            {
                throw new ShowServiceException("Show not found with ID: " + showId);
            }
            Show show = showOptional.get();

            List<ShowSeat> showSeatList = show.getShowSeatList();
            int totalSeats = showSeatList.size();
            ans.add("Total Seats for show with show  ID " + showId + " = " + totalSeats);
            int classic = 0, premium = 0, classicTotal = 0, premiumTotal = 0;

            for (ShowSeat showSeat : showSeatList)
            {
                if (showSeat.getSeatType().equals(SeatType.CLASSIC))
                {
                    classic++;
                    if (showSeat.getIsBooked())
                    {
                        classicTotal++;
                    }
                }
                else
                {
                    premium++;
                    if (showSeat.getIsBooked())
                    {
                        premiumTotal++;
                    }
                }
            }
            ans.add("Total Classic seats = " + classicTotal + ". Total Classic seats booked= " + classic);
            ans.add("Total Premium seats = " + premiumTotal + ". Total Premium seats booked= " + premium);
            return ans;
        }
        catch (ShowServiceException e)
        {
            throw new ShowServiceException("An error occurred while retrieving remaining seats", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public String deleteShow(Integer showId)throws ShowServiceException
    {
        try
        {
            Optional<Show> show = showRepository.findById(showId);
            if (show.isEmpty())
            {
                throw new ShowServiceException("Show not found with ID: " + showId);
            }

            showRepository.delete(show.get());
            return "The show with show ID " + showId + " has been successfully deleted from the database";
        }
        catch (ShowServiceException e)
        {
            throw new ShowServiceException("An error occurred while deleting the show", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public List<String> theatreListOfMovie(String movieName)throws ShowServiceException
    {
        try
        {
            List<Show> showList = showRepository.findAll();
            HashSet<String> st = new HashSet<>();
            if (showList.isEmpty())
            {
                throw new ShowServiceException("Show not found with movie name: " + movieName);
            }
            for (Show show : showList)
            {
                if (show.getMovie().getMovieName().equals(movieName))
                {
                    st.add(show.getTheatre().getTheatreName());
                }
            }
            return new ArrayList<>(st);
        }
        catch (ShowServiceException e)
        {
            throw new ShowServiceException("An error occurred while retrieving the theatre list", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public List<String> theatreListOfMovie(String movieName, LocalDate date)throws ShowServiceException
    {
        try
        {
            List<Show> showList = showRepository.findAll();
            List<String> ans = new ArrayList<>();
            if (showList.isEmpty())
            {
                throw new ShowServiceException("Show not found with movie name: " + movieName);
            }
            for (Show show : showList)
            {
                if (show.getMovie().getMovieName().equals(movieName) && show.getShowDate().equals(date))
                {
                    ans.add("Theatre name: " + show.getTheatre().getTheatreName() + " Movie time: " + show.getShowTime());
                }
            }
            return ans;
        }
        catch (ShowServiceException e)
        {
            throw new ShowServiceException("An error occurred while retrieving the theatre list", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public List<String> theatreListOfMovieInCity(String movieName,String city)throws ShowServiceException
    {
        try
        {
            List<Show> showList = showRepository.findAll();
            List<String> ans = new ArrayList<>();
            if (showList.isEmpty())
            {
                throw new ShowServiceException("Show not found with movie name: " + movieName);
            }
            for (Show show : showList)
            {
                if (show.getMovie().getMovieName().equals(movieName) && show.getTheatre().getCity().equals(city))
                {
                    ans.add("Theatre name: " + show.getTheatre().getTheatreName() + " Movie time: " + show.getShowTime());
                }
            }
            return ans;
        }
        catch (ShowServiceException e)
        {
            throw new ShowServiceException("An error occurred while retrieving the theatre list", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public List<String> theatreListOfMovieInCityDate(String movieName,String city,LocalDate date)throws ShowServiceException
    {
        try
        {
            List<Show> showList = showRepository.findAll();
            if (showList.isEmpty())
            {
                throw new ShowServiceException("Show not found with movie name: " + movieName);
            }

            List<String> ans = new ArrayList<>();
            for (Show show : showList)
            {
                if (show.getMovie().getMovieName().equals(movieName) && show.getTheatre().getCity().equals(city))
                {
                    ans.add("Theatre name: " + show.getTheatre().getTheatreName() + " Movie time: " + show.getShowTime());
                }
            }
            return ans;
        }
        catch (ShowServiceException e)
        {
            throw new ShowServiceException("An error occurred while retrieving the theatre list: " + e.getMessage(), e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public String getShowID(String movieName,String city,String theatreName,LocalDate date)throws ShowServiceException
    {
        try
        {
            List<Show> showList = showRepository.findAll();
            if (showList.isEmpty())
            {
                throw new ShowServiceException("No shows available");
            }
            for (Show show : showList)
            {
                if (show.getMovie().getMovieName().equals(movieName) && show.getTheatre().getCity().equals(city)
                        && show.getShowDate().equals(date) && show.getTheatre().getTheatreName().equals(theatreName))
                {
                    return "The show-ID of the given attributes is: " + show.getShowId();
                }
            }
            return "The show with given attributes is not in the database";
        }
        catch (ShowServiceException e)
        {
            throw new ShowServiceException("An error occurred while retrieving the show ID: " + e.getMessage(), e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    public Double collection(String movieName, LocalDate date)
    {
        List<Ticket> ticketList = ticketRepository.findAll();
        double totalRevenue = 0.00;
        for (Ticket ticket : ticketList)
        {
            if (ticket.getMovieName().equals(movieName) && ticket.getShowDate().equals(date))
            {
                totalRevenue += ticket.getTotalAmount();
            }
        }
        return totalRevenue;
    }

    public Double collection(String movieName)
    {
        List<Ticket> ticketList = ticketRepository.findAll();
        double totalRevenue = 0.00;
        for (Ticket ticket : ticketList)
        {
            if (ticket.getMovieName().equals(movieName))
            {
                totalRevenue += ticket.getTotalAmount();
            }
        }
        return totalRevenue;
    }
}
