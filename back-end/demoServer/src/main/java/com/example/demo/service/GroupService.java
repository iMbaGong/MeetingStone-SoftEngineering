package com.example.demo.service;

import com.example.demo.dao.GroupDAO;
import com.example.demo.entity.Group;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    GroupDAO groupDAO;

    public List<Group> list(){
        return groupDAO.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
    public Group get(int id){
        return groupDAO.findById(id).orElse(null);
    }

    public void addOrUpdate(Group group){
        groupDAO.save(group);
    }

    public List<User> getMembers(int groupId){
        return groupDAO.findById(groupId).map(Group::getMembers).orElse(null);
    }

    public List<Group> search(String keywords){
        return groupDAO.findAllByNameLikeOrInfoLike('%' + keywords + '%', '%' + keywords + '%');
    }
}
