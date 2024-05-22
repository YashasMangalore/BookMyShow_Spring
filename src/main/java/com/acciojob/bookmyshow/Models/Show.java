package com.acciojob.bookmyshow.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="shows")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Show
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer showId;

    @JoinColumn
    @ManyToOne
    private Movie movie;
    @JoinColumn
    @ManyToOne
    private Theatre theatre;
    @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)//bidirectional napping
    private List<ShowSeat> showSeatList=new ArrayList<>();

    private LocalDate showDate;
    private LocalTime showTime;
    private Integer classicSeatPrice;
    private Integer premiumSeatPrice;
}
