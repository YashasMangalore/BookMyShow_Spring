package BookMyShow.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="shows")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level =AccessLevel.PRIVATE)
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long showId;

    @JoinColumn
    @ManyToOne
    Movie movie;
    @JoinColumn
    @ManyToOne
    Theatre theatre;
    @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)//bidirectional mapping
    List<ShowSeat> showSeatList=new ArrayList<>();

    LocalDate showDate;
    LocalTime showTime;
    Integer screenNumber;
    Integer classicSeatPrice;
    Integer premiumSeatPrice;
}
