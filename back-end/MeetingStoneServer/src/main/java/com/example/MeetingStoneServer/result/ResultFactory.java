package com.example.MeetingStoneServer.result;

public class ResultFactory {

    public static Result buildSuccessResult(Object data) {
        return buildResult(ResultCode.SUCCESS, "成功", data);
    }

    public static Result buildSuccessResult(Object data,Object data2) {
        return buildResult(ResultCode.SUCCESS, "成功", data,data2);
    }

    public static Result buildFailResult(String message) {
        return buildResult(ResultCode.FAIL, message, null);
    }

    public static Result buildResult(ResultCode resultCode, String message, Object data) {
        return buildResult(resultCode.code, message, data);
    }

    public static Result buildResult(int resultCode, String message, Object data) {
        return new Result(resultCode, message, data);
    }

    public static Result buildResult(ResultCode resultCode, String message, Object data,Object data2) {
        return new Result(resultCode.code, message, data,data2);
    }
}
