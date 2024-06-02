package com.acciojob.bookmyshow.Repository;

import com.acciojob.bookmyshow.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String>
{
}
