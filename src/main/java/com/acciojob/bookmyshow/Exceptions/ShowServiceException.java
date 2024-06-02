package com.acciojob.bookmyshow.Exceptions;

public class ShowServiceException extends RuntimeException
{
    public ShowServiceException(String message)
    {
        super(message);
    }

    public ShowServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
