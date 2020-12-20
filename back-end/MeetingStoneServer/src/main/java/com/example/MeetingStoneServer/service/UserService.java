package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.dao.UserDAO;
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
    public User getById(int id){
        return userDAO.findById(id).orElse(null);
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
    public List<Course> getJoinedCourses(int id){
        User user = userDAO.findById(id).orElse(null);
        assert user != null;
        System.out.println("User:"+user.getUsernum()+" try to get courses");
        return user.getCourses();
    }

}
