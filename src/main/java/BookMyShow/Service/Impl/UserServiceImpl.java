package BookMyShow.Service.Impl;

import BookMyShow.Dto.Request.AddUserRequest;
import BookMyShow.Dto.Request.UpdateUserRequest;
import BookMyShow.Exceptions.UserServiceException;
import BookMyShow.Models.User;
import BookMyShow.Repository.UserRepository;
import BookMyShow.Service.UserService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final Map<String, String> verificationCodes = new HashMap<>();

    @Override
    public String addUser( AddUserRequest userRequest) throws UserServiceException {
        try {
            if (userRequest.getMobileNo().length() != 10) {
                throw new UserServiceException("Mobile number must be exactly 10 digits.");
            }

            // Create and save the User entity
            User user = User.builder()
                    .age(userRequest.getAge())
                    .emailId(userRequest.getEmailId())
                    .mobileNo(userRequest.getMobileNo())
                    .name(userRequest.getName())
                    .build();

            // Prepare and send welcome email using MimeMessage
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String body = "<p>Hi " + userRequest.getName() + ",</p>" +
                    "<p>Welcome to BookMyShow Application!</p>" +
                    "<p>Enjoy a bit more in life by booking a ticket to watch your favorite shows at your nearest cinema house.</p>" +
                    "<p>Regards,<br>BookMyShow Team</p>";

            helper.setTo(userRequest.getEmailId());
            helper.setFrom("springtestdummy@gmail.com");
            helper.setSubject("Welcome to BookMyShow Application");
            helper.setText(body, true); // Enable HTML content

            javaMailSender.send(mimeMessage);

            // Save the user to the database
            user = userRepository.save(user);
            return "The user has been saved to the Database with user-Id: " + user.getUserId();
        } catch ( MailException e) {
            throw new UserServiceException("Failed to send welcome email to the user.", e);
        } catch (Exception e) {
            throw new UserServiceException("An unexpected error occurred while adding the user.", e);
        }
    }
    @Override
    public String sendVerificationCode(String email) throws UserServiceException
    {
        try
        {
            User user = userRepository.findByEmailId(email);
            if (user == null)
            {
                throw new UserServiceException("User not found.");
            }

            String code = generateVerificationCode();
            verificationCodes.put(email, code);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(email);
            helper.setFrom("springtestdummy@gmail.com");
            helper.setSubject("Verification Code for Deletion");
            String body = "Your verification code is: " + code;
            helper.setText(body);
            javaMailSender.send(mimeMessage);

            return "Verification code sent to email.";
        }
        catch (MailException e)
        {
            throw new UserServiceException("Failed to send verification email.", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    private String generateVerificationCode()
    {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    @Override
    public String verifyAndDeleteUser(String email, String code)throws UserServiceException
    {
        try
        {
            String storedCode = verificationCodes.get(email);
            if (storedCode == null || !storedCode.equals(code))
            {
                throw new UserServiceException("Invalid verification code.");
            }

            User user = userRepository.findByEmailId(email);
            if (user == null)
            {
                throw new UserServiceException("User not found.");
            }
            userRepository.delete(user);
            verificationCodes.remove(email);

            return "User deleted successfully.";
        }
        catch (MailException e)
        {
            throw new UserServiceException("Failed to send verification email.", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }

    @Override
    public String verifyAndUpdateUser( UpdateUserRequest userRequest, String email, String code)throws UserServiceException
    {
        try
        {
            String storedCode = verificationCodes.get(email);
            if (storedCode == null || !storedCode.equals(code))
            {
                throw new UserServiceException("Invalid verification code.");
            }

            User user = userRepository.findByEmailId(email);
            if (user == null)
            {
                throw new UserServiceException("User not found.");
            }
            if (userRequest.getAge() != null)
            {
                user.setAge(userRequest.getAge());
            }
            if (userRequest.getName() != null)
            {
                user.setName(userRequest.getName());
            }
            if (userRequest.getEmailId() != null)
            {
                user.setEmailId(userRequest.getEmailId());
            }
            if (userRequest.getMobileNo() != null)
            {
                user.setMobileNo(userRequest.getMobileNo());
            }

            userRepository.save(user);
            verificationCodes.remove(email);
            return "User updated successfully.";
        }
        catch (MailException e)
        {
            throw new UserServiceException("Failed to send verification email.", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while sending the verification code.", e);
        }
    }
}
