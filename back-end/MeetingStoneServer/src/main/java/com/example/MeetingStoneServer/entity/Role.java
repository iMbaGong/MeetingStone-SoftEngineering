package com.example.MeetingStoneServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "role")
@JsonIgnoreProperties({"handle","hibernateLazyInitializer","users"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    String role;
    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private List<User> users;
}
