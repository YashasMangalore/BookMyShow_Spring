package BookMyShow.Exceptions;

import org.springframework.mail.MailException;

public class UserServiceException extends RuntimeException
{
    public UserServiceException(String message)
    {
        super(message);
    }

    public UserServiceException(String message, MailException e) {
    }

    public UserServiceException(String message, Exception e) {
    }
}
