package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.ReplyDAO;
import com.example.MeetingStoneServer.entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    @Autowired
    ReplyDAO replyDAO;

    public void update(Reply reply){
        replyDAO.save(reply);
    }

    public List<Reply> getMyReply(int id){
        return replyDAO.findAllByResponder_Id(id);
    }
    public void remove(int id){
        replyDAO.deleteById(id);
    }

    public Reply getById(int id){
        return replyDAO.findById(id).orElse(null);
    }

    public List<Reply> getByApply(int id){
        return replyDAO.findAllByApply_Id(id);
    }


}
