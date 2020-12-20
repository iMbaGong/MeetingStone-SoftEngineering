package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseDAO extends JpaRepository<Course,Integer> {

}
