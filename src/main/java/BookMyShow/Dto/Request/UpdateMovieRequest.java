package BookMyShow.Dto.Request;

import BookMyShow.Enums.LANGUAGE;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMovieRequest
{
    String movieName;
    LANGUAGE newLanguage;
    Double newRating;
    Double newDuration;
}
