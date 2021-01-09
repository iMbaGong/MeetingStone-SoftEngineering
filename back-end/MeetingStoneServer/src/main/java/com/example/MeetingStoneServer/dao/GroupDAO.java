package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupDAO extends JpaRepository<Group, Integer> {
    public List<Group> findAllByNameLikeOrInfoLike(String key1, String key2);

    public List<Group> findAllByCourse_Id(int id);

    public List<Group> findAllByNameLikeOrLeader_UsernameLikeOrId(String kw1, String kw2, int id, Pageable pageable);
    public int countByNameLikeOrLeader_UsernameLikeOrId(String kw1, String kw2, int id);

}
