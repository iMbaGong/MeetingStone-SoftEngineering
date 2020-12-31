package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.GroupDAO;
import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;


    public boolean isExist(String username) {
        User user = userDAO.findByUsernum(username);
        return null != user;
    }

    public User getById(int id) {
        return userDAO.findById(id).orElse(null);
    }

    public User getByUsernum(String usernum) {
        return userDAO.findByUsernum(usernum);
    }

    public User get(String user_num, String password) {
        return userDAO.findByUsernumAndPassword(user_num, password);
    }

    public void add(User user) {
        userDAO.save(user);
    }

    public List<Group> getJoinedGroups(int id) {
        User user = userDAO.findById(id).orElse(null);
        assert user != null;
        System.out.println("User:" + user.getUsernum() + " try to get groups");
        return user.getGroups();
    }

    public List<Group> getJoinedByCourse(int id, int courseId) {
        User user = userDAO.findById(id).orElse(null);
        assert user != null;
        List<Group> groups = user.getGroups();
        Iterator<Group> iterator = groups.iterator();
        while (iterator.hasNext()) {
            Group group = iterator.next();
            if (group.getCourse() == null) {
                iterator.remove();
            } else if (group.getCourse().getId() != courseId) {
                iterator.remove();
            }
        }
        return groups;
    }

    public List<User> search(String kw) {
        return userDAO.findAllByUsernameLikeOrUsernumLike('%' + kw + '%', '%' + kw + '%');
    }
}
