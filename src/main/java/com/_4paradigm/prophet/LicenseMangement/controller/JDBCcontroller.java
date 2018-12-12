package com._4paradigm.prophet.LicenseMangement.controller;

import com._4paradigm.prophet.LicenseMangement.entity.User;
import com._4paradigm.prophet.LicenseMangement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jdbc")
public class JDBCcontroller {
    @Autowired
    private UserService userService;
    @GetMapping(value="get")
    public List<User> getList(){
        return userService.getList();

    }


}
