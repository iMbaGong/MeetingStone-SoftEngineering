package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.entity.Message;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    MessageService messageService;

    @CrossOrigin
    @GetMapping("sendMessage")
    public Result sendMessage(@RequestBody Message message){
        messageService.update(message);
        return ResultFactory.buildSuccessResult(null);
    }
}
