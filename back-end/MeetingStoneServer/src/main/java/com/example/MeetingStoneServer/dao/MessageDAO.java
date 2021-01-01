package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDAO extends JpaRepository<Message,Integer> {
}
