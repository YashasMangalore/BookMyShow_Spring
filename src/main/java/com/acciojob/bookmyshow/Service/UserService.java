package com.acciojob.bookmyshow.Service;

import com.acciojob.bookmyshow.Models.User;
import com.acciojob.bookmyshow.Repository.UserRepository;
import com.acciojob.bookmyshow.Requests.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;
    public String addUser(AddUserRequest userRequest)
    {
        User user=User.builder()
                .age(userRequest.getAge())
                .emailId(userRequest.getEmailId())
                .mobileNo(userRequest.getMobileNo())
                .name(userRequest.getName())
                .build();
        user=userRepository.save(user);
        return "The user has been saved to the Database with user-Id: "+user.getUserId();
    }
}
