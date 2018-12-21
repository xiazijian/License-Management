package com._4paradigm.prophet.LicenseMangement.service;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class Interceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器了");
        Object user = request.getSession().getAttribute("user");
        if (user==null){
            System.out.println("sesison中没有用户名");
            PrintWriter printWriter = response.getWriter();
            printWriter.write("{code:403,message:\"session is invalid,please login again !\"}");
            return false;
        }
        return true;
    }

}
