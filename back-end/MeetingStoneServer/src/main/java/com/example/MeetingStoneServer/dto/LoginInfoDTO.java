package com.example.MeetingStoneServer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfoDTO {
    int id;
    String username;
    String usernum;
    String token;
}
