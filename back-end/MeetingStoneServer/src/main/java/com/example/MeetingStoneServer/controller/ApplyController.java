package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.config.JwtConfig;
import com.example.MeetingStoneServer.dto.ApplyDTO;
import com.example.MeetingStoneServer.entity.Apply;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.ApplyService;
import com.example.MeetingStoneServer.service.CourseService;
import com.example.MeetingStoneServer.service.GroupService;
import com.example.MeetingStoneServer.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public Result updateApply(@RequestBody ApplyDTO applyDTO){
        if(applyDTO.getType()==1|| applyDTO.getType()==3){
            applyDTO.getTags().add(applyDTO.getCourse().getName());
        }
        Apply apply = Apply.builder().build();
        BeanUtils.copyProperties(applyDTO,apply);
        apply.setApplicant(userService.getById(jwtConfig.getUserId(applyDTO.getApplicant())));
        apply.setTags(applyDTO.getTags().toString());
        if (apply.getCourse().getId()==0){
            apply.setCourse(null);
        }
        if (apply.getGroup().getId()==0){
            apply.setGroup(null);
        }
        applyService.Update(apply);
        return ResultFactory.buildSuccessResult(null);
    }

}
