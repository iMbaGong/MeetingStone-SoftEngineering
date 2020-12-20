package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyDAO extends JpaRepository<Apply,Integer> {
    List<Apply> findAllByApplicant_Id(int id);
}
