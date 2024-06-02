package com.acciojob.bookmyshow.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="theatres")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Theatre
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer theatreId;
    @OneToMany(mappedBy = "theatre",cascade = CascadeType.ALL)
    private List<TheatreSeat> theatreSeatList=new ArrayList<>();

    private Integer noOfScreens;
    private String theatreName;
    private String city;
}
