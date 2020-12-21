package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Apply;
import com.example.MeetingStoneServer.entity.Course;
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
}
