package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.CourseService;
import com.example.MeetingStoneServer.service.GroupService;
import com.example.MeetingStoneServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GroupController {

    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;


    @CrossOrigin
    @GetMapping("initGroup")
    public Result init() {
        return ResultFactory.buildSuccessResult(groupService.list());
    }

    @CrossOrigin
    @GetMapping("group")
    public Result group(@RequestParam("groupId") int groupId) {
        return ResultFactory.buildSuccessResult(groupService.getById(groupId));
    }

    @CrossOrigin
    @GetMapping("changeLeader")
    public Result changeLeader(@RequestParam("groupId") int groupId, @RequestParam("userId") int userId) {
        Group group = groupService.getById(groupId);
        group.setLeader(userService.getById(userId));
        groupService.addOrUpdate(group);
        return ResultFactory.buildSuccessResult(null);
    }

    @CrossOrigin
    @GetMapping("removeGroupMember")
    public Result removeMember(@RequestParam("groupId") int groupId, @RequestParam("userId") int userId) {
        Group group = groupService.getById(groupId);
        if(group.getLeader().getId()==userId){
            groupService.removeById(groupId);
            return ResultFactory.buildSuccessResult(null);
        }
        List<User> members = group.getMembers();
        Iterator<User>iterator = members.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getId()==userId) {
                iterator.remove();
                break;
            }
        }
        group.setMembers(members);
        groupService.addOrUpdate(group);
        return ResultFactory.buildSuccessResult(null);
    }

    @CrossOrigin
    @GetMapping("addGroupMember")
    public Result addGroupMember(@RequestParam("groupId") int groupId, @RequestParam("userId") int userId) {
        Group group = groupService.getById(groupId);
        List<User> members = group.getMembers();
        Iterator<User>iterator = members.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getId()==userId) {
                return ResultFactory.buildFailResult("用户已在小组中");
            }
        }
        members.add(userService.getById(userId));
        group.setMembers(members);
        groupService.addOrUpdate(group);
        return ResultFactory.buildSuccessResult(null);
    }

    @CrossOrigin
    @GetMapping("/courseGroups")
    Result getMembers(@RequestParam("courseId")int id){
        return ResultFactory.buildSuccessResult(groupService.getByCourse(id));
    }

    @CrossOrigin
    @PostMapping("/createGroup")
    Result createGroup(@RequestBody Group group){
        groupService.addOrUpdate(group);
        return ResultFactory.buildSuccessResult(null);
    }
}
