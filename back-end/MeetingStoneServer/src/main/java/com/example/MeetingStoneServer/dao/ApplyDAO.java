package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Apply;
import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ApplyDAO extends JpaRepository<Apply,Integer> {
    List<Apply> findAllByApplicant_Id(int id);
    List<Apply> findAllByCourseIsInAndTitleLikeOrTagsLike(Collection<Course> courses,
                                                          String title,
                                                          String tags,
                                                          Pageable pageable);
    List<Apply> findAllByCourseIsInAndTitleLike(Collection<Course> courses,
                                                String title);
    List<Apply> findAllByTitleLike(String kw);

    List<Apply> findAllByCourseIsInAndApplicantIsNotAndStateOrTypeIsInAndApplicantIsNotAndState(Collection<Course> courses,
                                                               User user1,int state1,
                                                               Collection<Integer> type,User user2,int state2);
}
