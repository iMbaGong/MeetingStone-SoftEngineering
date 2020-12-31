package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDAO extends JpaRepository<Course,Integer> {
    public List<Course> findAllByStudentsContainingOrTeacher_Id(User user,int id);

}
