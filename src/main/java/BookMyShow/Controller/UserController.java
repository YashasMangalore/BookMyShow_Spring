package BookMyShow.Controller;

import BookMyShow.Dto.Request.AddUserRequest;
import BookMyShow.Dto.Request.UpdateUserRequest;
import BookMyShow.Exceptions.UserServiceException;
import BookMyShow.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "HEllo";    }

    @PostMapping("/add") // adds a user to DB
    public ResponseEntity<String> addUser( @RequestBody AddUserRequest userRequest) throws UserServiceException
    {
        try
        {
            String response = userService.addUser(userRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (UserServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while adding the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/send-code") // verification code before updating
    public ResponseEntity<String> sendUpdateVerificationCode(@RequestBody String email) throws UserServiceException
    {
        try
        {
            String response = userService.sendVerificationCode(email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (UserServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while sending the verification code.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update") // update user
    public ResponseEntity<String> updateUser(@RequestParam String email, @RequestParam String code, @RequestBody UpdateUserRequest userRequest)  throws UserServiceException
    {
        try
        {
            String response = userService.verifyAndUpdateUser(userRequest, email, code);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (UserServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while updating the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/send-code") // verification code before deleting
    public ResponseEntity<String> sendDeleteVerificationCode(@RequestParam String email) throws UserServiceException
    {
        try
        {
            String response = userService.sendVerificationCode(email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (UserServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while sending the verification code.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete") // deleting a user from DB
    public ResponseEntity<String> deleteUser(@RequestParam String email, @RequestParam String code) throws UserServiceException
    {
        try
        {
            String response = userService.verifyAndDeleteUser(email, code);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (UserServiceException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("An unexpected error occurred while deleting the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
