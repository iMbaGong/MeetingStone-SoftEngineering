package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.CourseDAO;
import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

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

    public List<Course> search(String kw, Pageable pageable){
        Pattern pattern = Pattern.compile("[0-9]+");
        int id;
        if(!pattern.matcher(kw).matches())
            id = 0;
        else
            id = Integer.parseInt(kw);
        return courseDAO.findAllByNameLikeOrTeacher_UsernameLikeOrId
                ('%' + kw + '%', '%' + kw + '%',id,pageable);
    }

    public int count(String kw){
        Pattern pattern = Pattern.compile("[0-9]+");
        int id;
        if(!pattern.matcher(kw).matches())
            id = 0;
        else
            id = Integer.parseInt(kw);
        return courseDAO.countByNameLikeOrTeacher_UsernameLikeOrId
                ('%' + kw + '%', '%' + kw + '%',id);
    }
}
