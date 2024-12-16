package BookMyShow.Dto.Request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTheatreRequest
{
    String name;

    String newTheatreName;
    Integer newNoOfScreens;
    String newCity;
}
