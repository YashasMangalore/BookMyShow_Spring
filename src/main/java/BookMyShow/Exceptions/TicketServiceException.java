package BookMyShow.Exceptions;

public class TicketServiceException extends RuntimeException
{
    public TicketServiceException(String message)
    {
        super(message);
    }
}
