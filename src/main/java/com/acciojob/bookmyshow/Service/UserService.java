package com.acciojob.bookmyshow.Service;

import com.acciojob.bookmyshow.Exceptions.UserServiceException;
import com.acciojob.bookmyshow.Models.User;
import com.acciojob.bookmyshow.Repository.UserRepository;
import com.acciojob.bookmyshow.Requests.AddUserRequest;
import com.acciojob.bookmyshow.Requests.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService
{
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    private final Map<String, String> verificationCodes = new HashMap<>();

    public String addUser(AddUserRequest userRequest) throws UserServiceException
    {
        try
        {
            if (userRequest.getMobileNo().length() != 10)
            {
                throw new UserServiceException("Mobile number must be exactly 10 digits.");
            }

            User user = User.builder()
                    .age(userRequest.getAge())
                    .emailId(userRequest.getEmailId())
                    .mobileNo(userRequest.getMobileNo())
                    .name(userRequest.getName())
                    .build();

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userRequest.getEmailId());
            mailMessage.setFrom("springtestdummy@gmail.com");
            mailMessage.setSubject("Welcome to BookMyShow Application");
            String body = "Hi " + userRequest.getName() + " \n \n" + "Welcome to BookMyShow Application. Enjoy a bit more in life by booking a ticket to watch your favorite shows at your nearest cinema house.";
            mailMessage.setText(body);
            javaMailSender.send(mailMessage);

            user = userRepository.save(user);
            return "The user has been saved to the Database with user-Id: " + user.getUserId();
        }
        catch (MailException e)
        {
            throw new UserServiceException("Failed to send welcome email to the user.", e);
        }
        catch (Exception e)
        {
            throw new UserServiceException("An unexpected error occurred while adding the user.", e);
        }
    }

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

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setFrom("springtestdummy@gmail.com");
            mailMessage.setSubject("Verification Code for Deletion");
            String body = "Your verification code is: " + code;
            mailMessage.setText(body);
            javaMailSender.send(mailMessage);

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

    public String verifyAndUpdateUser(UpdateUserRequest userRequest, String email, String code)throws UserServiceException
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