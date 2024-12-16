package BookMyShow.Models;

import BookMyShow.Enums.SEAT_TYPE;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="show_seats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long showSeatId;
    @JoinColumn
    @ManyToOne
    Show show;
    @Enumerated(value = EnumType.STRING)
    SEAT_TYPE seatType;

    String seatNo;
    Boolean isBooked;
}
