package com.acciojob.bookmyshow.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Integer noOfScreens;
    private String name;
    private String address;
}
