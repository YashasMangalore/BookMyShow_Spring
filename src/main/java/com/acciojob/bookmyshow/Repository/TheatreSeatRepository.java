package com.acciojob.bookmyshow.Repository;

import com.acciojob.bookmyshow.Models.TheatreSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreSeatRepository extends JpaRepository<TheatreSeat, Integer>
{
    List<TheatreSeat> findByTheatreTheatreId(Integer theatreId);
}
