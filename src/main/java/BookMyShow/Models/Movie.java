package BookMyShow.Models;

import BookMyShow.Enums.LANGUAGE;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name ="movies")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long movieId;

    @Column(unique=true)
    String movieName;

    @Enumerated(value=EnumType.STRING)
    LANGUAGE language;

    Double duration;
    Double ratings;
    LocalDate releaseDate;
}
