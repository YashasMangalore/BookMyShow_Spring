package com.acciojob.bookmyshow.Repository;

import com.acciojob.bookmyshow.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show,Integer>
{
}
