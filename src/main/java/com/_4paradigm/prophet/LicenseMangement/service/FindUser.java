package com._4paradigm.prophet.LicenseMangement.service;

import com._4paradigm.prophet.LicenseMangement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FindUser {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Boolean findUser(User user){
        String sql = "select * from user where name = ? and password = ?";
        Object [] args = {user.getName(),user.getPassword()};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,args);
        System.out.println("数据是："+list);
        if(list.isEmpty()) return false;
        else return true;
    }
}
