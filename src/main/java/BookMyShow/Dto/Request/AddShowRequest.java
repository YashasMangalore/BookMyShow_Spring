package BookMyShow.Dto.Request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddShowRequest
{
    LocalDate showDate;
    LocalTime showTime;
    Integer classicSeatPrice;
    Integer premiumSeatPrice;
    Integer screenNumber;
    String movieName;
    Long theatreId;
}
