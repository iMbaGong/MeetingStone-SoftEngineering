package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupDAO extends JpaRepository<Group,Integer> {
    List<Group> findAllByNameLikeOrInfoLike(String key1, String key2);
}
