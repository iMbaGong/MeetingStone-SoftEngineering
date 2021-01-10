package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.config.JwtConfig;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return ResultFactory.buildSuccessResult
                (userService.getJoinedGroups(jwtConfig.getUserId(token)));
    }
    @CrossOrigin
    @GetMapping("api/userGroups")
    public Result getGroupsByUser(@RequestParam("userId")int id){
        return ResultFactory.buildSuccessResult
                (userService.getJoinedGroups(id));
    }

    @CrossOrigin
    @GetMapping("api/myGroupByCourse")
    public Result myGroupByCourse(@RequestHeader("token")String token,
                                  @RequestParam("courseId")int id){
        return ResultFactory.buildSuccessResult
                (userService.getJoinedByCourse(jwtConfig.getUserId(token),id));
    }

    @CrossOrigin
    @GetMapping("api/searchUser")
    public Result searchUser(@RequestParam("keyword")String kw){
        return ResultFactory.buildSuccessResult(userService.search(kw));
    }

    @CrossOrigin
    @GetMapping("api/searchUser/{page}")
    public Result searchWithPage(@PathVariable("page")int page,@RequestParam("kw")String kw){
        Pageable pageable = PageRequest.of
                (page-1,10, Sort.by(Sort.Direction.ASC,"usernum"));
        return ResultFactory.buildSuccessResult(userService.search(kw,pageable),userService.count(kw));
    }


}
