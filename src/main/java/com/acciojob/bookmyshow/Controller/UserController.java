package com.acciojob.bookmyshow.Controller;

import com.acciojob.bookmyshow.Requests.AddUserRequest;
import com.acciojob.bookmyshow.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController
{
    @Autowired
    private UserService userService;
    @PostMapping("add")
    public String addUser(@RequestBody AddUserRequest userRequest)
    {
        return userService.addUser(userRequest);
    }
}
