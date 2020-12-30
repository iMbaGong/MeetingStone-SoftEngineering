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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user2role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") }, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Role> roles;

}

