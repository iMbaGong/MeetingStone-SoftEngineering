package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GroupController {

    @Autowired
    GroupService groupService;


    @CrossOrigin
    @GetMapping("initGroup")
    public Result init(){
        return ResultFactory.buildSuccessResult(groupService.list());
    }

    @CrossOrigin
    @GetMapping("group")
    public Result group(@RequestParam("groupId")int groupId){
        return ResultFactory.buildSuccessResult(groupService.getById(groupId));
    }

}
