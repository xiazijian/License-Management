package com._4paradigm.prophet.LicenseMangement.controller;


import com._4paradigm.prophet.LicenseMangement.entity.User;
import com._4paradigm.prophet.LicenseMangement.service.FindUser;
import com._4paradigm.prophet.rest.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private FindUser findUser;
    @PostMapping (value="login")
    public ResponseEntity<Response> login(@RequestBody User user, HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {
        System.out.println(user);
        Response res = new Response();
        Boolean result = findUser.findUser(user);
        response.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
        //PrintWriter printWriter = response.getWriter();
        if(result) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("user",user.getName());
            session.setMaxInactiveInterval(10000);
            System.out.println("在login controller里设置里session："+session.getId());
            res.setStatus("200");
            res.setData(user);
           // printWriter.write("{code:200,name:"+user.getName()+"}");
        }

        else{
            res.setStatus("403");
            res.setMsg("用户名或密码错误");
            //printWriter.write("{code:403,message:\"用户名或密码错误\"}");
        }
        ResponseEntity<Response> responseEntity = new ResponseEntity<>(res, HttpStatus.OK);
        return responseEntity;
    }


}
