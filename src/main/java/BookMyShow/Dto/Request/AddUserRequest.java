package BookMyShow.Dto.Request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddUserRequest
{
    String name;
    String mobileNo;
    String emailId;
    Integer age;
}