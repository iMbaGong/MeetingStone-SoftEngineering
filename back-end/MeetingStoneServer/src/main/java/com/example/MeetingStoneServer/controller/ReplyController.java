package com.example.MeetingStoneServer.controller;


import com.alibaba.fastjson.JSONArray;
import com.example.MeetingStoneServer.config.JwtConfig;
import com.example.MeetingStoneServer.dto.ApplyDTO;
import com.example.MeetingStoneServer.dto.ReplyDTO;
import com.example.MeetingStoneServer.entity.Apply;
import com.example.MeetingStoneServer.entity.Reply;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.ApplyService;
import com.example.MeetingStoneServer.service.ReplyService;
import com.example.MeetingStoneServer.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class ReplyController {
    @Autowired
    ReplyService replyService;
    @Autowired
    ApplyService applyService;
    @Autowired
    UserService userService;
    @Resource
    JwtConfig jwtConfig;


    @CrossOrigin
    @PostMapping("/reply")
    public Result createReply(@RequestHeader("token")String token,@RequestBody ReplyDTO replyDTO){
        Reply reply = new Reply();
        BeanUtils.copyProperties(replyDTO,reply);
        reply.setApply(DTO2Apply(replyDTO.getApply(),token));
        reply.setResponder(userService.getById(jwtConfig.getUserId(token)));
        replyService.update(reply);
        return ResultFactory.buildSuccessResult(null);
    }


    public Apply DTO2Apply(ApplyDTO applyDTO,String token){
        Apply apply = new Apply();
        BeanUtils.copyProperties(applyDTO,apply);
        apply.setTags(JSONArray.toJSONString(applyDTO.getTags()));
        return apply;
    }
}
