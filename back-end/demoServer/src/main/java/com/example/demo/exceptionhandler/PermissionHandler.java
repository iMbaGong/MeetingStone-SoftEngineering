package com.example.demo.exceptionhandler;

import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PermissionHandler {

    @ExceptionHandler(value = {SignatureException.class})
    public Result authorizationException(SignatureException e){
        return ResultFactory.buildFailResult(e.getMessage());
    }
}
