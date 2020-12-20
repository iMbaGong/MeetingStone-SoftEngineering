package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.CourseDAO;
import com.example.MeetingStoneServer.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    CourseDAO courseDAO;

    public Course getById(int id){
        return courseDAO.findById(id).orElse(null);
    }
}
