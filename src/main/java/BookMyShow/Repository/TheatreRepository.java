package BookMyShow.Repository;

import BookMyShow.Models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Long>
{
    Theatre findTheatreByTheatreName( String theatreName);
}
