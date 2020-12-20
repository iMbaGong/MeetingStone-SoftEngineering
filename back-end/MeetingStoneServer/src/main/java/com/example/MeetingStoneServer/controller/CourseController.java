package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.config.JwtConfig;
import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.CourseService;
import com.example.MeetingStoneServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;
    @Resource
    JwtConfig jwtConfig;
    @CrossOrigin
    @GetMapping("/joinedCourses")
    Result getAll(@RequestHeader("token")String token){
        return ResultFactory.buildSuccessResult(userService.getJoinedCourses(jwtConfig.getUserId(token)));
    }
}