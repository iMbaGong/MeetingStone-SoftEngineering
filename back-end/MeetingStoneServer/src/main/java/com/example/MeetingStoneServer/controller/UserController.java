package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.config.JwtConfig;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Resource
    JwtConfig jwtConfig;
    @CrossOrigin
    @GetMapping("api/joinedGroups")
    public Result getJoinedGroups(@RequestHeader("token")String token){
        return ResultFactory.buildSuccessResult(userService.getJoinedGroups(jwtConfig.getUserId(token)));
    }

    @CrossOrigin
    @GetMapping("api/myGroupByCourse")
    public Result myGroupByCourse(@RequestHeader("token")String token,
                                  @RequestParam("courseId")int id){
        return ResultFactory.buildSuccessResult(userService.getJoinedByCourse(jwtConfig.getUserId(token),id));
    }

    @CrossOrigin
    @GetMapping("api/searchUser")
    public Result searchUser(@RequestParam("keyword")String kw){
        return ResultFactory.buildSuccessResult(userService.search(kw));
    }
}
