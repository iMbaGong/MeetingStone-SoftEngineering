package com.example.MeetingStoneServer.dto;

import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDTO {
    int id;
    int type;
    String remark;
    ApplyDTO apply;
    User responder;
    Group group;
}
