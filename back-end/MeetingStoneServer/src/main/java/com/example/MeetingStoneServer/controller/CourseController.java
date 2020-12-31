package com.example.MeetingStoneServer.controller;

import com.example.MeetingStoneServer.config.JwtConfig;
import com.example.MeetingStoneServer.dto.CourseDTO;
import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.result.Result;
import com.example.MeetingStoneServer.result.ResultFactory;
import com.example.MeetingStoneServer.service.CourseService;
import com.example.MeetingStoneServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;
    @Resource
    JwtConfig jwtConfig;

    @CrossOrigin
    @GetMapping("/joinedCourses")
    Result getAll(@RequestHeader("token") String token) {
        List<Course> courses = courseService.getByUser(userService.getById(jwtConfig.getUserId(token)));
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course cours : courses) {
            courseDTOS.add(CourseDTO.toDTO(cours, userService.getById(cours.getAssist())));
        }
        return ResultFactory.buildSuccessResult(courseDTOS);
    }

    @CrossOrigin
    @GetMapping("/courseMembers")
    Result getMembers(@RequestParam("courseId") int id) {
        return ResultFactory.buildSuccessResult(courseService.getById(id).getStudents());
    }

    @CrossOrigin
    @GetMapping("/setAssist")
    Result setAssist(@RequestParam("courseId") int cid, @RequestParam("userId") int uid) {
        Course course = courseService.getById(cid);
        User user = userService.getById(uid);
        if (user == null)
            course.setAssist(0);
        else
            course.setAssist(user.getId());
        courseService.update(course);
        return ResultFactory.buildSuccessResult(null);
    }
}
