package com.example.demo.service;

import com.example.demo.entity.Group;
import com.example.demo.entity.User;
import com.example.demo.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String username) {
        User user = userDAO.findByUsernum(username);
        return null!=user;
    }

    public User getByName(String username) {
        return userDAO.findByUsername(username);
    }

    public User getByUsernum(String usernum){
        return userDAO.findByUsernum(usernum);
    }

    public User get(String user_num, String password){
        return userDAO.findByUsernumAndPassword(user_num, password);
    }

    public void add(User user) {
        userDAO.save(user);
    }

    public List<Group> getJoinedGroups(int id){
        User user = userDAO.findById(id).orElse(null);
        assert user != null;
        System.out.println("User:"+user.getUsernum()+" try to get groups");
        return user.getGroups();
    }

}
