package BookMyShow.Dto.Request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookTicketRequest {
    Long showId;
    List<String> requestedSeats;
    Long userId;
}
