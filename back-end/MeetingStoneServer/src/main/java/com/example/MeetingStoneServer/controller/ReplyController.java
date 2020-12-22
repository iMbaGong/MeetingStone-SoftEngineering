package com.example.MeetingStoneServer.controller;


import com.alibaba.fastjson.JSONArray;
import com.example.MeetingStoneServer.config.JwtConfig;
import com.example.MeetingStoneServer.dto.ApplyDTO;
import com.example.MeetingStoneServer.dto.ReplyDTO;
import com.example.MeetingStoneServer.entity.Apply;
import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.entity.Reply;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.ApplyService;
import com.example.MeetingStoneServer.service.GroupService;
import com.example.MeetingStoneServer.service.ReplyService;
import com.example.MeetingStoneServer.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReplyController {
    @Autowired
    ReplyService replyService;
    @Autowired
    ApplyService applyService;
    @Autowired
    UserService userService;
    @Autowired
    GroupService groupService;
    @Resource
    JwtConfig jwtConfig;


    @CrossOrigin
    @PostMapping("/reply")
    public Result createReply(@RequestHeader("token")String token,@RequestBody ReplyDTO replyDTO){
        User user = userService.getById(jwtConfig.getUserId(token));
        List<Reply> replies = replyService.getMyReply(user.getId());
        for (Reply reply:replies){
            if (reply.getApply().getId()==replyDTO.getApply().getId()){
                return ResultFactory.buildFailResult("不能进行重复操作");
            }
        }
        Reply reply = new Reply();
        BeanUtils.copyProperties(replyDTO,reply);
        reply.setApply(replyDTO.getApply().toEntity());
        reply.setResponder(user);
        if(reply.getType()==1){
            reply.setGroup(null);
        }
        replyService.update(reply);
        Apply apply = reply.getApply();
        apply.setUnhandled(apply.getUnhandled()+1);
        applyService.update(apply);
        return ResultFactory.buildSuccessResult(null);
    }

    @CrossOrigin
    @GetMapping("myReply")
    public Result myReply(@RequestHeader("token")String token){
        return ResultFactory.buildSuccessResult(ReplyDTO.toDTO(replyService.getMyReply(jwtConfig.getUserId(token))));
    }

    @CrossOrigin
    @GetMapping("withdrawReply")
    public Result withdrawApply(@RequestParam("replyId")int id){
        replyService.remove(id);
        return ResultFactory.buildSuccessResult("删除成功");
    }

    @CrossOrigin
    @GetMapping("reply/{replyId}/{state}")
    public Result updateState(@PathVariable("replyId") int id,@PathVariable("state") int state){
        Reply reply = replyService.getById(id);
        reply.setState(state);
        replyService.update(reply);
        Apply apply = reply.getApply();
        apply.setUnhandled(apply.getUnhandled()-1);
        applyService.update(apply);
        if(reply.getType()==1){
            Group group = apply.getGroup();
            List<User>  members= group.getMembers();
            User responder=reply.getResponder();
            for (User user:members) {
                if (user.getId()== responder.getId()){
                    return ResultFactory.buildFailResult("用户已在小组中。");
                }
            }
            group.getMembers().add(reply.getResponder());
            groupService.addOrUpdate(group);
        }
        else if(reply.getType()==2){
            Group group = reply.getGroup();
            List<User>  members= group.getMembers();
            User applicant = apply.getApplicant();
            for (User user:members) {
                if (user.getId()== applicant.getId()){
                    return ResultFactory.buildFailResult("用户已在小组中。");
                }
            }
            group.getMembers().add(applicant);
            groupService.addOrUpdate(group);
        }
        return ResultFactory.buildSuccessResult(null);
    }

    @CrossOrigin
    @GetMapping("replyByApply")
    public Result replyByApply(@RequestParam("applyId")int id){
        return ResultFactory.buildSuccessResult(replyService.getByApply(id));
    }
}
