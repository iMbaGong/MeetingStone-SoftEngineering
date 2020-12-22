package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyDAO extends JpaRepository<Reply,Integer> {
    public List<Reply> findAllByResponder_Id(int id);
    public List<Reply> findAllByApply_Id(int id);
}
