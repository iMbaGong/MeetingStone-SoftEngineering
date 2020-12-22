package com.example.MeetingStoneServer.dto;

import com.example.MeetingStoneServer.entity.Group;
import com.example.MeetingStoneServer.entity.Reply;
import com.example.MeetingStoneServer.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReplyDTO {
    int id;
    int type;
    int state;
    String remark;
    ApplyDTO apply;
    User responder;
    Group group;
    Date crtDate;


    static public ReplyDTO toDTO(Reply reply){
        ReplyDTO replyDTO = new ReplyDTO();
        BeanUtils.copyProperties(reply,replyDTO);
        replyDTO.setApply(ApplyDTO.toDTO(reply.getApply()));
        return replyDTO;
    }
    static public List<ReplyDTO> toDTO(List<Reply> replies){
        List<ReplyDTO> replyDTOList = new ArrayList<>();
        for (Reply reply : replies) {
            replyDTOList.add(toDTO(reply));
        }
        return replyDTOList;
    }

}
