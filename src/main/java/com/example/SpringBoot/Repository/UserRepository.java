package com.example.SpringBoot.Repository;

import com.example.SpringBoot.Entity.User;
import com.example.SpringBoot.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
