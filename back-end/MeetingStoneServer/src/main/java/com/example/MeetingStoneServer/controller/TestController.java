package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.config.JwtConfig;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class TestController {
    @Resource
    private JwtConfig jwtConfig ;
    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String sayHello1(@RequestHeader("token")String token){

        Claims claims = jwtConfig.getTokenClaim(token);
        String usernum = claims.getSubject();
        System.out.println("Hello:"+usernum);
        User user = userService.getByUsernum(usernum);
        return user.getUsername();
    }




}
