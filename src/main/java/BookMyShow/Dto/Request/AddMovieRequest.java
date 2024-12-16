package BookMyShow.Dto.Request;

import BookMyShow.Enums.LANGUAGE;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddMovieRequest
{
    String movieName;
    @Enumerated(value = EnumType.STRING)
    LANGUAGE language;
    Double duration;
    Double ratings;
    LocalDate releaseDate;
}
