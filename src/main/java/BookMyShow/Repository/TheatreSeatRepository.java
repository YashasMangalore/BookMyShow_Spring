package BookMyShow.Repository;

import BookMyShow.Models.TheatreSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreSeatRepository extends JpaRepository<TheatreSeat, Long>
{
    List<TheatreSeat> findByTheatreTheatreId( Long theatreId);
}
