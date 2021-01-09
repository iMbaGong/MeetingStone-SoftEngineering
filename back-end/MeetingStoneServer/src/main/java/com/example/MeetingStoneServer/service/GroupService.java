package com.example.MeetingStoneServer.service;

import com.example.MeetingStoneServer.dao.GroupDAO;
import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.regex.Pattern;

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
    public List<Group> getByCourse(int id){
        return groupDAO.findAllByCourse_Id(id);
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

    public List<Group> search(String kw, Pageable pageable){
        Pattern pattern = Pattern.compile("[0-9]+");
        int id;
        if(!pattern.matcher(kw).matches())
            id = 0;
        else
            id = Integer.parseInt(kw);
        return groupDAO.findAllByNameLikeOrLeader_UsernameLikeOrId
                ('%' + kw + '%', '%' + kw + '%',id,pageable);
    }

    public int count(String kw){
        Pattern pattern = Pattern.compile("[0-9]+");
        int id;
        if(!pattern.matcher(kw).matches())
            id = 0;
        else
            id = Integer.parseInt(kw);
        return groupDAO.countByNameLikeOrLeader_UsernameLikeOrId
                ('%' + kw + '%', '%' + kw + '%',id);
    }
}
