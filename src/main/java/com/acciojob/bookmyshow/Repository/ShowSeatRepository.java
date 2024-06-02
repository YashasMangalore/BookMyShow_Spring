package com.acciojob.bookmyshow.Repository;

import com.acciojob.bookmyshow.Models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Integer>
{

}
