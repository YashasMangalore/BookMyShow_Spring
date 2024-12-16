package BookMyShow.Dto.Request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddTheatreSeatRequest //DTO's are custom classes helping in taking info from clients
{
    Long theatreId;
    Integer noOfClassicSeats;
    Integer noOfPremiumSeats;
}