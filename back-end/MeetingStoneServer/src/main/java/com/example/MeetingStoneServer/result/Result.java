package com.example.MeetingStoneServer.result;

import lombok.Data;

@Data
public class Result {
    //响应码
    private int code;
    private String message;
    private Object result;
    private Object result2;

    Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.result = data;
    }

    Result(int code, String message, Object data,Object data2) {
        this.code = code;
        this.message = message;
        this.result = data;
        this.result2 = data2;
    }
}

