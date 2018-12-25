package com._4paradigm.prophet.LicenseMangement.controller;

import com._4paradigm.prophet.rest.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value = "api")
public class JudgeLogin {
    @GetMapping(value = "judgeSession")
    public ResponseEntity<Response> juedeSession(HttpServletRequest httpServletRequest){
        ResponseEntity<Response> responseEntity;
        Response res = new Response();
        Object user = httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            res.setMsg("没有登录，请重新登录");
            responseEntity = new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
        }
        else{
            res.setMsg("已登录");
            responseEntity = new ResponseEntity<>(res, HttpStatus.OK);
        }
        return responseEntity;
    }
}
