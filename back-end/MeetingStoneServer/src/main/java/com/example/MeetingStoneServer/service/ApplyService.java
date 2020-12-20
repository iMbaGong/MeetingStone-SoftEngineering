package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.ApplyDAO;
import com.example.MeetingStoneServer.entity.Apply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyService {
    @Autowired
    ApplyDAO applyDAO;

    public void update(Apply apply){
        applyDAO.save(apply);
    }

    public void remove(int id){
        applyDAO.deleteById(id);
    }

    public List<Apply> getByUserId(int id){
        return applyDAO.findAllByApplicant_Id(id);
    }
}
