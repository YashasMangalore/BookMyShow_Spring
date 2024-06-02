package com.acciojob.bookmyshow.Exceptions;

public class MovieServiceException extends RuntimeException
{
    public MovieServiceException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MovieServiceException(String message)
    {
        super(message);
    }
}


