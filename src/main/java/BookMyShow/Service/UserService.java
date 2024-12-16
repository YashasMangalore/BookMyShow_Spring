package BookMyShow.Service;

import BookMyShow.Dto.Request.AddUserRequest;
import BookMyShow.Dto.Request.UpdateUserRequest;

public interface UserService {
    String verifyAndUpdateUser( UpdateUserRequest userRequest, String email, String code);
    String verifyAndDeleteUser(String email, String code);
    String sendVerificationCode(String email);
    String addUser( AddUserRequest userRequest);

}
