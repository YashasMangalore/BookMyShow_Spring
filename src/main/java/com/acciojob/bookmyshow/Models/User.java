package com.acciojob.bookmyshow.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String name;
    private String mobileNo;
    private String emailId;
    private Integer age;
}
