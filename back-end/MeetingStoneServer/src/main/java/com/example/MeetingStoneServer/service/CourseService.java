package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.CourseDAO;
import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseDAO courseDAO;

    public Course getById(int id){
        return courseDAO.findById(id).orElse(null);
    }

    public List<Course> getByUser(User user){
        return courseDAO.findAllByStudentsContainingOrTeacher_Id(user,user.getId());
    }

    public void update(Course course){
        courseDAO.save(course);
    }

}
