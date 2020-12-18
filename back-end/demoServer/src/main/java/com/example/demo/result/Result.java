package com.example.demo.result;

import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;

@Data
public class Result {
    //响应码
    private int code;
    private String message;
    private Object result;

    Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.result = data;
    }
}

