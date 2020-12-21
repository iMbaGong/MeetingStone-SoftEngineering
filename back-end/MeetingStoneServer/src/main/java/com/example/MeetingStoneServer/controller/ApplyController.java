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
    public Result updateApply(@RequestBody ApplyDTO applyDTO){
        if(applyDTO.getType()==1|| applyDTO.getType()==3){
            applyDTO.getTags().add(applyDTO.getCourse().getName());
        }

        Apply apply = Apply.builder().build();
        BeanUtils.copyProperties(applyDTO,apply);
        apply.setApplicant(userService.getById(jwtConfig.getUserId(applyDTO.getApplicant())));
        apply.setTags(JSONArray.toJSONString(applyDTO.getTags()));
        if (apply.getCourse().getId()==0){
            apply.setCourse(null);
        }
        if (apply.getGroup().getId()==0){
            apply.setGroup(null);
        }
        applyService.update(apply);
        return ResultFactory.buildSuccessResult(null);
    }


    @CrossOrigin
    @GetMapping("/myApply")
    public Result myApply(@RequestHeader("token")String token){
        System.out.println("get apply");
        List<Apply> applies = applyService.getByUserId(jwtConfig.getUserId(token));
        return ResultFactory.buildSuccessResult(apply2DTO(applies));
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
        Pageable pageable = PageRequest.of(pageNum,pageSize, Sort.by(Sort.Direction.ASC,"crtDate"));
        return ResultFactory.buildSuccessResult(null);
    }









    private List<ApplyDTO> apply2DTO(List<Apply> applies){
        List<ApplyDTO> dtos = new ArrayList<>();
        for(Apply apply:applies){
            ApplyDTO applyDTO = new ApplyDTO();
            BeanUtils.copyProperties(apply,applyDTO);
            applyDTO.setTags(JSONArray.parseArray(apply.getTags()).toJavaList(String.class));
            dtos.add(applyDTO);
        }
        return dtos;
    }

}
