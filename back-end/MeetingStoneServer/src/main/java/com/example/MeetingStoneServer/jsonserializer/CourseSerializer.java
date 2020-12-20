package com.example.MeetingStoneServer.jsonserializer;

import com.example.MeetingStoneServer.entity.Course;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CourseSerializer extends JsonSerializer<Course> {
    @Override
    public void serialize(Course course, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(course.getId()==0){
            jsonGenerator.writeObject(null);
        }
    }
}
