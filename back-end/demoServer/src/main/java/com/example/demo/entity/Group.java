package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
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
    @ManyToOne
    @JoinColumn(name = "leader")
    private User leader;
    String info;
    int type;
    String tags;
    @ManyToMany
    @JoinTable(name = "group2user", joinColumns = { @JoinColumn(name = "group_id") }, inverseJoinColumns = {
            @JoinColumn(name = "user_id") }, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<User> members;

}