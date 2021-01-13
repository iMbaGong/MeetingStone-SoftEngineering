package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.dto.ApplyDTO;
import com.example.MeetingStoneServer.entity.Apply;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/apply")
public class AdminApplyController {
    @Autowired
    ApplyService applyService;

    @CrossOrigin
    @PostMapping("basic")
    public Result update(@RequestBody ApplyDTO applyDTO){
        applyService.update(applyDTO.toEntity());
        return ResultFactory.buildSuccessResult(null);
    }

    @CrossOrigin
    @GetMapping("remove")
    public Result remove(@RequestParam("applyId")int id){
        applyService.remove(id);
        return ResultFactory.buildSuccessResult(null);
    }

    @CrossOrigin
    @GetMapping("state/{state}")
    public Result reject(@RequestParam("applyId")int id,@PathVariable("state")int state){
        Apply apply = applyService.getById(id);
        apply.setState(state);
        applyService.update(apply);
        return ResultFactory.buildSuccessResult(null);
    }
}
