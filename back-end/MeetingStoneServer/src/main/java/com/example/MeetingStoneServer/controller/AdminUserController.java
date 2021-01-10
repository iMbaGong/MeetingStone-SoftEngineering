package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminUserController {
    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping("user/basic")
    public Result updateUser(@RequestBody User newUser){
        User user = userService.getById(newUser.getId());
        if(user ==null){
            return ResultFactory.buildFailResult("无此用户");
        }
        user.setUsername(newUser.getUsername());
        user.setUsernum(newUser.getUsernum());
        user.setRoles(newUser.getRoles());
        userService.add(user);
        return ResultFactory.buildSuccessResult(null);
    }

    @CrossOrigin
    @GetMapping("resetPassword")
    public Result resetPw(@RequestParam("userId")int id){
        User user = userService.getById(id);
        String encodedPassword = new SimpleHash("md5", "123", user.getSalt(), 2).toString();
        user.setPassword(encodedPassword);
        return ResultFactory.buildSuccessResult(null);
    }
}
