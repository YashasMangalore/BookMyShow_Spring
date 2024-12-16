package BookMyShow.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="tickets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String ticketId;
    @Column(unique = true)
    String movieName;

    String bookedSeats;
    String theatreName;
    Integer screenNumber;
    LocalDate showDate;
    LocalTime showTime;
    Integer totalAmount;

    @JoinColumn
    @ManyToOne
    Show show;
    @JoinColumn
    @ManyToOne
    User user;
}
