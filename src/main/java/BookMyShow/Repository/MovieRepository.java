package BookMyShow.Repository;

import BookMyShow.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>
{
    Movie findMovieByMovieName( String movieName);
}
