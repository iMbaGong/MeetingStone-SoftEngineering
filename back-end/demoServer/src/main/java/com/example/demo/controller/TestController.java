package com.example.demo.controller;

import com.example.demo.config.JwtConfig;
import com.example.demo.entity.User;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import com.example.demo.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
