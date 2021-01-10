package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.CourseService;
import com.example.MeetingStoneServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminCourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping("course/basic")
    public Result update(@RequestBody Course course){
        courseService.update(course);
        return ResultFactory.buildSuccessResult(null);
    }

    @CrossOrigin
    @GetMapping("course/addMember")
    public Result addMember(@RequestParam("courseId")int courseId,@RequestParam("userId")int userId){
        Course course = courseService.getById(courseId);
        if(course==null){
            return  ResultFactory.buildFailResult("无此课程");
        }
        List<User> members = course.getStudents();
        User user = userService.getById(userId);
        for (User u :
                members) {
            if (u.getId() == userId)
                return  ResultFactory.buildFailResult("已在课程中");
        }
        members.add(user);
        course.setStudents(members);
        courseService.update(course);
        return ResultFactory.buildSuccessResult(null);
    }

    @CrossOrigin
    @GetMapping("course/removeMember")
    public Result removeMember(@RequestParam("courseId")int courseId,@RequestParam("userId")int userId){
        Course course = courseService.getById(courseId);
        if(course==null){
            return  ResultFactory.buildFailResult("无此课程");
        }
        List<User> members = course.getStudents();
        Iterator<User> iterator = members.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getId()==userId) {
                iterator.remove();
                break;
            }
        }
        course.setStudents(members);
        courseService.update(course);
        return ResultFactory.buildSuccessResult(null);
    }

    @CrossOrigin
    @GetMapping("course/remove")
    public Result remove(@RequestParam("courseId")int id){
        courseService.remove(id);
        return ResultFactory.buildSuccessResult(null);
    }
}
