package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/group")
public class AdminGroupController {
    @Autowired
    GroupService groupService;

    @CrossOrigin
    @GetMapping("remove")
    public Result remove(@RequestParam("groupId")int id){
        groupService.removeById(id);
        return ResultFactory.buildSuccessResult(null);
    }
}
