package com.example.MeetingStoneServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "course")
@JsonIgnoreProperties({"handle","hibernateLazyInitializer","students"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    User teacher;
    @Column(name = "assist_id")
    int assist;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "course2user", joinColumns = { @JoinColumn(name = "course_id") }, inverseJoinColumns = {
            @JoinColumn(name = "user_id") }, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    List<User> students;
}
