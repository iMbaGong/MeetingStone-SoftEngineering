package com.example.MeetingStoneServer.entity;

import com.example.MeetingStoneServer.dto.ApplyDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apply")
@JsonIgnoreProperties({"handle","hibernateLazyInitializer"})
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    String title;
    String intro;
    String tags;
    @Column(name = "crt_date")
    Date crtDate;
    @Column(name = "ddl_date")
    Date ddlDate;
    int state;
    int type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    User applicant;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    Group group;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    Course course;
}

