package com.acciojob.bookmyshow.Models;

import com.acciojob.bookmyshow.Enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="theatre_seats")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TheatreSeat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer theatreSeatId;

    @Enumerated(value=EnumType.STRING)
    private SeatType seatType;

    @JoinColumn
    @ManyToOne
    private Theatre theatre;//always join in child

    private String seatNo;
}
