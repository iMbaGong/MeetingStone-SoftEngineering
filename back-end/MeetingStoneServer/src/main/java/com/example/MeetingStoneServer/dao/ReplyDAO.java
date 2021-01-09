package com.example.MeetingStoneServer.dao;

import com.example.MeetingStoneServer.entity.Reply;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyDAO extends JpaRepository<Reply, Integer> {
    public List<Reply> findAllByResponder_Id(int id);

    public List<Reply> findAllByApply_Id(int id);

    public List<Reply> findAllByRemarkLikeOrApply_TitleLikeOrResponder_UsernameLikeOrId(String kw1, String kw2,  String kw3, int id,Pageable pageable);
    public int countByRemarkLikeOrApply_TitleLikeOrResponder_UsernameLikeOrId(String kw1, String kw2, String kw3,int id);
}
