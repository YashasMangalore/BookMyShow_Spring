package BookMyShow.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class TicketResponse
{
    String bookedSeats;
    String movieName;
    String theatreName;
    Integer screenNumber;
    LocalDate showDate;
    LocalTime showTime;
    Integer totalAmount;
}
