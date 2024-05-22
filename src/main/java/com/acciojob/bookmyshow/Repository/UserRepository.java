package com.acciojob.bookmyshow.Repository;

import com.acciojob.bookmyshow.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>
{
}
