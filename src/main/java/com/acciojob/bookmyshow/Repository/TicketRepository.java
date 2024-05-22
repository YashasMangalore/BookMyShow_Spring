package com.acciojob.bookmyshow.Repository;

import com.acciojob.bookmyshow.Models.Ticket;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String>
{
}
