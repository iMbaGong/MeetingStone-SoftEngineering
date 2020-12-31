package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.ApplyDAO;
import com.example.MeetingStoneServer.entity.Apply;
import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ApplyService {
    @Autowired
    ApplyDAO applyDAO;

    public void update(Apply apply){
        applyDAO.save(apply);
    }

    public void remove(int id){
        applyDAO.deleteById(id);
    }

    public List<Apply> search(Collection<Course> courses,
                              String title,
                              String tags,
                              Pageable pageable){
        return applyDAO.findAllByCourseIsInAndTitleLikeOrTagsLike(courses,title,tags,pageable);
    }
    public List<Apply> search(Collection<Course> courses,
                              String title){
        return applyDAO.findAllByCourseIsInAndTitleLike(courses,title);
    }

    public List<Apply> search(String keyword){
        return applyDAO.findAllByTitleLike(keyword);
    }
    public List<Apply> search(Collection<Course> courses, User user){
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(4);
        return applyDAO.findAllByCourseIsInAndApplicantIsNotAndStateOrTypeIsInAndApplicantIsNotAndState(courses,user,1,list,user,1);
    }

    public List<Apply> getByUserId(int id){
        return applyDAO.findAllByApplicant_Id(id);
    }
}
