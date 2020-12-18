package com.example.demo.controller;

import com.example.demo.entity.Group;
import com.example.demo.entity.User;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import com.example.demo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GroupController {

    @Autowired
    GroupService groupService;




    @CrossOrigin
    @GetMapping("api/groupMember")
    public List<User> getMembers(@RequestParam("name")int groupId){
        return groupService.getMembers(groupId);
    }

    @CrossOrigin
    @GetMapping("api/searchGroup")
    List<Group> search(@RequestParam("keywords")String keywords){
        if("".equals(keywords))
            return groupService.list();
        else
            return groupService.search(keywords);
    }

    @CrossOrigin
    @GetMapping("api/initGroup")
    @ResponseBody
    public Result init(){
        return ResultFactory.buildSuccessResult(groupService.list());
    }
}
