package BookMyShow.Exceptions;

public class TheatreServiceException extends RuntimeException {
    public TheatreServiceException(String message)
    {
        super(message);
    }
}
