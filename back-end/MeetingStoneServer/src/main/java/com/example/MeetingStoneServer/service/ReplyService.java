package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.ReplyDAO;
import com.example.MeetingStoneServer.entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    @Autowired
    ReplyDAO replyDAO;

    public void update(Reply reply){
        replyDAO.save(reply);
    }

}
