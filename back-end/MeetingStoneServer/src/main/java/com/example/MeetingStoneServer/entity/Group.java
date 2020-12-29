package com.example.MeetingStoneServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projectgroup")
@JsonIgnoreProperties({"handle","hibernateLazyInitializer"})
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader")
    private User leader;
    String info;
    int type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    Course course;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group2user", joinColumns = { @JoinColumn(name = "group_id") }, inverseJoinColumns = {
            @JoinColumn(name = "user_id") }, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<User> members;
    @Column(name = "crt_date")
    Date crtDate;
}
