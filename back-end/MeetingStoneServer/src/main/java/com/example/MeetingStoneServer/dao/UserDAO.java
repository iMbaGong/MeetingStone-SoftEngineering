package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByUsernum(String usernum);
    User findByUsernumAndPassword(String usernum,String password);
    List<User> findAllByUsernameLikeOrUsernumLike(String kw1,String kw2);
}
