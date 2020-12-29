package com.example.MeetingStoneServer.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.MeetingStoneServer.config.JwtConfig;
import com.example.MeetingStoneServer.dto.ApplyDTO;
import com.example.MeetingStoneServer.entity.Apply;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.ApplyService;
import com.example.MeetingStoneServer.service.CourseService;
import com.example.MeetingStoneServer.service.GroupService;
import com.example.MeetingStoneServer.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.Column;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApplyController {
    @Autowired
    ApplyService applyService;
    @Autowired
    GroupService groupService;
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;
    @Resource
    JwtConfig jwtConfig;

    @CrossOrigin
    @PostMapping("/apply")
    public Result updateApply(@RequestHeader("token")String token, @RequestBody ApplyDTO applyDTO){
        Apply apply = applyDTO.toEntity();
        apply.setApplicant(userService.getById(jwtConfig.getUserId(token)));
        applyService.update(apply);
        return ResultFactory.buildSuccessResult(null);
    }


    @CrossOrigin
    @GetMapping("/myApply")
    public Result myApply(@RequestHeader("token")String token){
        System.out.println("get apply");
        List<Apply> applies = applyService.getByUserId(jwtConfig.getUserId(token));
        return ResultFactory.buildSuccessResult(ApplyDTO.toDTO(applies));
    }

    @CrossOrigin
    @GetMapping("withdrawApply")
    public Result withdrawApply(@RequestParam("applyId")int id){
        applyService.remove(id);
        return ResultFactory.buildSuccessResult("删除成功");
    }

    @CrossOrigin
    @GetMapping("searchApply")
    public Result searchApply(
            @RequestHeader("token")String token,
                                @RequestParam("pageSize")int pageSize,
                                @RequestParam("pageNum")int pageNum,
                                @RequestParam("keyword")String keyword){
        User user = userService.getById(jwtConfig.getUserId(token));
        keyword = keyword.equals("#")?"%":keyword;
        Pageable pageable = PageRequest.of(pageNum,pageSize, Sort.by(Sort.Direction.ASC,"crtDate"));
        return ResultFactory.buildSuccessResult(ApplyDTO.toDTO(applyService.search(user.getCourses(),keyword)));
    }

    @CrossOrigin
    @GetMapping("allApply")
    public Result allApply(@RequestHeader("token")String token){
        User user = userService.getById(jwtConfig.getUserId(token));
        return ResultFactory.buildSuccessResult(ApplyDTO.toDTO(applyService.search(user.getCourses(),user)));
    }


}
