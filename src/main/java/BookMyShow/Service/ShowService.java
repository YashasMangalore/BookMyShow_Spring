package BookMyShow.Service;

import BookMyShow.Dto.Request.AddShowRequest;
import BookMyShow.Dto.Request.UpdateShowRequest;

import java.time.LocalDate;
import java.util.List;

public interface ShowService {
    String addShow( AddShowRequest showRequest);
    String updateShow( UpdateShowRequest showRequest);
    List<String> seatsRemaining( Long showId);
    String deleteShow(Long showId);
    List<String> theatreListOfMovie(String movieName);
    List<String> theatreListOfMovie(String movieName, LocalDate date);
    List<String> theatreListOfMovieInCity(String movieName,String city);
    List<String> theatreListOfMovieInCityDate(String movieName,String city,LocalDate date);
    String getShowID(String movieName,String city,String theatreName,LocalDate date);
    Double collection(String movieName, LocalDate date);
    Double collection(String movieName);
}
