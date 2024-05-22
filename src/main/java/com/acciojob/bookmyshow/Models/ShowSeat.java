package com.acciojob.bookmyshow.Models;

import com.acciojob.bookmyshow.Enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="show_seats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer showSeatId;
    @JoinColumn
    @ManyToOne
    private Show show;
    @Enumerated(value = EnumType.STRING)
    private SeatType seatType;

    private String seatNo;
    private Boolean isBooked;
    private Boolean isFoodAttached;
}
