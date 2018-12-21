package com._4paradigm.prophet.LicenseMangement.controller;


import com._4paradigm.prophet.LicenseMangement.entity.User;
import com._4paradigm.prophet.LicenseMangement.service.FindUser;
import com._4paradigm.prophet.rest.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private FindUser findUser;
    @PostMapping(value="login")
    public ResponseEntity<Response> login(@RequestBody User user, HttpServletResponse response, HttpSession httpSession) throws IOException {
        System.out.println(user);
        Response res = new Response();
        Boolean result = findUser.findUser(user);
        //PrintWriter printWriter = response.getWriter();
        if(result) {
            httpSession.setAttribute("user",user.getName());

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
