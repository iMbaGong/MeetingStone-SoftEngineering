package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.ReplyDAO;
import com.example.MeetingStoneServer.entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

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

    public List<Reply> search(String kw, Pageable pageable) {
        Pattern pattern = Pattern.compile("[0-9]+");
        int id;
        if(!pattern.matcher(kw).matches())
            id = 0;
        else
            id = Integer.parseInt(kw);
        return replyDAO.findAllByRemarkLikeOrApply_TitleLikeOrResponder_UsernameLikeOrId('%' + kw + '%', '%' + kw + '%','%' + kw + '%',id,pageable);
    }

    public int count(String kw) {
        Pattern pattern = Pattern.compile("[0-9]+");
        int id;
        if(!pattern.matcher(kw).matches())
            id = 0;
        else
            id = Integer.parseInt(kw);
        return replyDAO.countByRemarkLikeOrApply_TitleLikeOrResponder_UsernameLikeOrId('%' + kw + '%', '%' + kw + '%', '%' + kw + '%',id);
    }

}
