package com.example.MeetingStoneServer.entity;

public enum Type {
    GroupInClass(1),
    GroupOutClass(2),
    PersonalInClass(3),
    PersonalOutClass(4);

    private int index;
    Type(int i){
        index=i;
    }
}
