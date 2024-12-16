package BookMyShow.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="theatres")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long theatreId;
    @OneToMany(mappedBy = "theatre",cascade = CascadeType.ALL)
    List<TheatreSeat> theatreSeatList=new ArrayList<>();

    Integer noOfScreens;
    String theatreName;
    String city;
}
