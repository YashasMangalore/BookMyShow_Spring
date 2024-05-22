package com.acciojob.bookmyshow.Service;

import com.acciojob.bookmyshow.Models.*;
import com.acciojob.bookmyshow.Repository.MovieRepository;
import com.acciojob.bookmyshow.Repository.ShowRepository;
import com.acciojob.bookmyshow.Repository.ShowSeatRepository;
import com.acciojob.bookmyshow.Repository.TheatreRepository;
import com.acciojob.bookmyshow.Requests.AddShowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public String addShow(AddShowRequest showRequest)
    {
        Movie movie = movieRepository.findMovieByMovieName(showRequest.getMovieName());
        if (movie == null)
        {
            throw new IllegalArgumentException("Movie with name " + showRequest.getMovieName() + " not found.");
        }

        Optional<Theatre> theatreOptional = theatreRepository.findById(showRequest.getTheatreId());
        if (theatreOptional.isEmpty())
        {
            throw new IllegalArgumentException("Theatre with ID " + showRequest.getTheatreId() + " not found.");
        }
        Theatre theatre = theatreOptional.get();

        Show show=Show.builder()
                .showDate(showRequest.getShowDate())
                .showTime(showRequest.getShowTime())
                .classicSeatPrice(showRequest.getClassicSeatPrice())
                .premiumSeatPrice(showRequest.getPremiumSeatPrice())
                .movie(movie)
                .theatre(theatre)
                .build();
        show=showRepository.save(show);

        //associate the corresponding show seats along with it
        List<TheatreSeat> theatreSeatList=theatre.getTheatreSeatList();
        List<ShowSeat> showSeatList=new ArrayList<>();

        for(TheatreSeat theatreSeat:theatreSeatList)
        {
            ShowSeat showSeat=ShowSeat.builder()
                    .seatNo(theatreSeat.getSeatNo())
                    .seatType(theatreSeat.getSeatType())
                    .isBooked(Boolean.FALSE)
                    .isFoodAttached(Boolean.FALSE)
                    .show(show)
                    .build();

            showSeatList.add(showSeat);
        }

        show.setShowSeatList(showSeatList);//setting bidirectional mapping

        showSeatRepository.saveAll(showSeatList);
        return "The show has been saved to the DataBase with showId: "+show.getShowId();

    }
}
