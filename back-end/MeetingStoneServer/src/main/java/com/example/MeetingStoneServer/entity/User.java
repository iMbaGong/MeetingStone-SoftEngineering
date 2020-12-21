package com.example.MeetingStoneServer.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler","hibernateLazyInitializer","groups","courses","password","salt"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    String username;
    String password;
    @Column(name = "user_num")
    String usernum;
    String salt;

    @ManyToMany(mappedBy="members",fetch = FetchType.LAZY)
    private List<Group> groups;
    @ManyToMany(mappedBy = "students",fetch = FetchType.LAZY)
    private List<Course> courses;

}

