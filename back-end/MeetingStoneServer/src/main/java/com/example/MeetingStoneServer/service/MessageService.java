package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.MessageDAO;
import com.example.MeetingStoneServer.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;

    public void update(Message message){
        messageDAO.save(message);
    }
}
