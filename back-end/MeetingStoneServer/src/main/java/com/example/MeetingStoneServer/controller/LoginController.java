package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.config.JwtConfig;
import com.example.MeetingStoneServer.dto.LoginInfoDTO;
import com.example.MeetingStoneServer.dto.UserDTO;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Resource
    JwtConfig jwtConfig;

    // 设置 hash 算法迭代次数
    int hashTimes=2;

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody UserDTO requestUser) {
        String userNum = requestUser.getUsernum();
        String password = requestUser.getPassword();
        User user = userService.getByUsernum(userNum);
        if(user==null){
            return ResultFactory.buildFailResult("找不到用户");
        }
        String encodedPassword = new SimpleHash("md5", password, user.getSalt(), hashTimes).toString();
        if(!encodedPassword.equals(user.getPassword())){
            return ResultFactory.buildFailResult("用户名或密码错误");
        }
        System.out.println(user.getUsernum()+" hello");
        String token = jwtConfig.createToken(user.getId()+"");
        LoginInfoDTO loginInfoDTO = new LoginInfoDTO();
        loginInfoDTO.setId(user.getId());
        loginInfoDTO.setToken(token);
        loginInfoDTO.setUsername(user.getUsername());
        loginInfoDTO.setUsernum(user.getUsernum());
        return ResultFactory.buildSuccessResult(loginInfoDTO);
    }

    @CrossOrigin
    @PostMapping(value = "api/register")
    @ResponseBody
    public Result register(@RequestBody UserDTO userDTO) {
        String usernum = userDTO.getUsernum();
        String password = userDTO.getPassword();
        usernum = HtmlUtils.htmlEscape(usernum);
        User user = new User();
        user.setUsernum(usernum);

        boolean exist = userService.isExist(usernum);
        if (exist) {
            String message = "学号已被使用";
            return ResultFactory.buildFailResult(message);
        }

        // 生成盐,默认长度 16 位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", password, salt, hashTimes).toString();
        // 存储用户信息，包括 salt 与 hash 后的密码
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        userService.add(user);
        return ResultFactory.buildSuccessResult(user);
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("api/logout")
    public Result logout() {
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "api/authentication")
    public Result authentication(){
        return ResultFactory.buildSuccessResult(null);
    }

}

