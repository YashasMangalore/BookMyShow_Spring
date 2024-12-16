package BookMyShow.Models;

import BookMyShow.Enums.SEAT_TYPE;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name="theatre_seats")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TheatreSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long theatreSeatId;
    @Enumerated(value=EnumType.STRING)
    SEAT_TYPE seatType;
    @JoinColumn
    @ManyToOne
    Theatre theatre;//always join in child

    String seatNo;
}
