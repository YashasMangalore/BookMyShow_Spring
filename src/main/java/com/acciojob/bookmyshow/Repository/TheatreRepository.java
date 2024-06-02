package com.acciojob.bookmyshow.Repository;

import com.acciojob.bookmyshow.Models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Integer>
{
    Theatre findTheatreByTheatreName(String theatreName);
}
