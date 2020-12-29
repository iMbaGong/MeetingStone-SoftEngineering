package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.GroupDAO;
import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    GroupDAO groupDAO;

    public List<Group> list(){
        return groupDAO.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
    public Group getById(int id){
        return groupDAO.findById(id).orElse(null);
    }

    public void addOrUpdate(Group group){
        groupDAO.save(group);
    }
    public void removeById(int id){
        groupDAO.deleteById(id);
    }

    public List<User> getMembers(int groupId){
        return groupDAO.findById(groupId).map(Group::getMembers).orElse(null);
    }

    public List<Group> search(String keywords){
        return groupDAO.findAllByNameLikeOrInfoLike('%' + keywords + '%', '%' + keywords + '%');
    }
}
