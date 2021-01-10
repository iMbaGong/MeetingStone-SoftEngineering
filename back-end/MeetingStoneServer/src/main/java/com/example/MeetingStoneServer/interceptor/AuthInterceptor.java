package com.example.MeetingStoneServer.interceptor;

import com.example.MeetingStoneServer.config.JwtConfig;
import com.example.MeetingStoneServer.entity.Role;
import com.example.MeetingStoneServer.entity.User;
import com.example.MeetingStoneServer.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    private JwtConfig jwtConfig;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }
        System.out.println("需要管理员权限");
        String token = request.getHeader(jwtConfig.getHeader());
        if(StringUtils.isEmpty(token)){
            throw new SignatureException(jwtConfig.getHeader()+ "不能为空");
        }
        List<String> roles = jwtConfig.getRoles(token);
        for (String role :
                roles) {
            if (role.equals("admin"))
                return true;
        }

        return false;
    }
}
