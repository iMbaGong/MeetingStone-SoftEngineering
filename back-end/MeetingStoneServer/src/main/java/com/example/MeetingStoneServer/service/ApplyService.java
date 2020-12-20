package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.ApplyDAO;
import com.example.MeetingStoneServer.entity.Apply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {
    @Autowired
    ApplyDAO applyDAO;

    public void Update(Apply apply){
        applyDAO.save(apply);
    }
}
