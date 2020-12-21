package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyDAO extends JpaRepository<Reply,Integer> {
}
