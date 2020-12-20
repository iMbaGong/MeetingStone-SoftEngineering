package com.example.MeetingStoneServer.dto;

import com.alibaba.fastjson.JSONArray;
import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.jsonserializer.CourseSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;


import java.util.Date;
import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplyDTO {
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
    String applicant;
}
