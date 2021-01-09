package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDAO extends JpaRepository<Course,Integer> {
    public List<Course> findAllByStudentsContainingOrTeacher_Id(User user,int id);
    public List<Course> findAllByNameLikeOrTeacher_UsernameLikeOrId(String kw1, String kw2, int id, Pageable pageable);
    public int countByNameLikeOrTeacher_UsernameLikeOrId(String kw1, String kw2, int id);
}
