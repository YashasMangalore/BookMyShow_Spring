package com.acciojob.bookmyshow.Requests;

import lombok.Data;

@Data
public class AddUserRequest
{
    private String name;
    private String mobileNo;
    private String emailId;
    private Integer age;
}
