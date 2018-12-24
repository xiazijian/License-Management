package com._4paradigm.prophet.LicenseMangement.service;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class Interceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器了");
        //解决跨域问题
        //response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type");

        System.out.println("request.getMethod():"+request.getMethod());
        System.out.println("request.getServletPath()"+request.getServletPath());

        Object user = request.getSession().getAttribute("user");
        System.out.println("拦截器里取得的session："+request.getSession().getId());
        System.out.println(user);
        if (!request.getMethod().equals("OPTIONS")&&user==null&&!request.getServletPath().equals("/login")){
            System.out.println("sesison中没有用户名");
            PrintWriter printWriter = response.getWriter();
            response.setStatus(403);
            printWriter.write("{code:403,message:\"session is invalid,please login again !\"}");
            return false;
        }
        return true;
    }

}
