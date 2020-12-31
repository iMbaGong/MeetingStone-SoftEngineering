package com.example.MeetingStoneServer.dto;

import com.example.MeetingStoneServer.entity.Course;
import com.example.MeetingStoneServer.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class CourseDTO {
    int id;
    String name;
    User teacher;
    User assist;

    static public CourseDTO toDTO(Course course,User _assist){
        CourseDTO dto = new CourseDTO();
        BeanUtils.copyProperties(course,dto);
        dto.setAssist(_assist);
        return dto;
    }
}
