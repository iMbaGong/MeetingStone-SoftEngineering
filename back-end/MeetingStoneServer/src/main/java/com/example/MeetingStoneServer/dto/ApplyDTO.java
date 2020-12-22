package com.example.MeetingStoneServer.dto;

import com.alibaba.fastjson.JSONArray;
import com.example.MeetingStoneServer.entity.Apply;
import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.jsonserializer.CourseSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplyDTO {
    User applicant;
    int id;
    String title;
    List<String> tags;
    String intro;
    Date crtDate;
    Date ddlDate;
    int type;
    int state;
    //@JsonSerialize(using = CourseSerializer.class)
    Course course;
    Group group;
    int unhandled;

    public Apply toEntity(){
        if(type==1|| type==3){
            tags.add(course.getName());
        }
        Apply apply = new Apply();
        BeanUtils.copyProperties(this,apply);
        apply.setTags(JSONArray.toJSONString(tags));
        if (apply.getCourse()!=null&&apply.getCourse().getId()==0){
            apply.setCourse(null);
        }
        if (apply.getGroup()!=null&&apply.getGroup().getId()==0){
            apply.setGroup(null);
        }
        return apply;
    }

    static public List<ApplyDTO> toDTO(List<Apply> applies){
        List<ApplyDTO> dtos = new ArrayList<>();
        for(Apply apply:applies){
            ApplyDTO applyDTO = new ApplyDTO();
            BeanUtils.copyProperties(apply,applyDTO);
            applyDTO.setTags(JSONArray.parseArray(apply.getTags()).toJavaList(String.class));
            dtos.add(applyDTO);
        }
        return dtos;
    }


    static public ApplyDTO toDTO(Apply apply){
        ApplyDTO applyDTO = new ApplyDTO();
        BeanUtils.copyProperties(apply,applyDTO);
        applyDTO.setTags(JSONArray.parseArray(apply.getTags()).toJavaList(String.class));
        return applyDTO;
    }
}
