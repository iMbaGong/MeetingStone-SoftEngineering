package com.example.demo.controller;

import com.example.demo.config.JwtConfig;
import com.example.demo.entity.Group;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Resource
    JwtConfig jwtConfig;
    @CrossOrigin
    @GetMapping("api/joined")
    @ResponseBody
    public Result getJoinedGroups(@RequestParam("userId")int userId,@RequestHeader("token")String token){
        return ResultFactory.buildSuccessResult(userService.getJoinedGroups(userId));
    }
}
