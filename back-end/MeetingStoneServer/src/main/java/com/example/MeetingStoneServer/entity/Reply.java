package com.example.MeetingStoneServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "reply")
@JsonIgnoreProperties({"handle","hibernateLazyInitializer"})
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int type;
    String intro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responder_id")
    User responder;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id")
    Apply apply;
}
