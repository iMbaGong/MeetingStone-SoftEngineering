package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByUsernum(String usernum);
    User findByUsernumAndPassword(String usernum,String password);
    User findByUsernameAndPassword(String username, String password);
}
