package BookMyShow.Service;

import BookMyShow.Dto.Request.AddTheatreRequest;
import BookMyShow.Dto.Request.AddTheatreSeatRequest;
import BookMyShow.Dto.Request.UpdateTheatreRequest;

import java.time.LocalDate;
import java.util.List;

public interface TheatreService {
    List<String> movieListInTheatre( String theatreName, LocalDate date);
    List<String> movieListInTheatre(String theatreName);
    List<String> theatreListInCity(String city);
    String delete(Long theatreId);
    String updateTheatreAttributes( UpdateTheatreRequest theatreRequest);
    Double revenue(String theatreName, LocalDate date);
    String associateTheatreSeats( AddTheatreSeatRequest theatreSeatRequest);
    String addTheatre( AddTheatreRequest theatreRequest);
}
