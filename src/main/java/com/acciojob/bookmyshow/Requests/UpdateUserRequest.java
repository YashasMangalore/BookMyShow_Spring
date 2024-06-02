package com.acciojob.bookmyshow.Requests;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UpdateUserRequest
{
    private Integer userId;

    private String mobileNo;
    private String emailId;
    private String name;
    private Integer age;
}
