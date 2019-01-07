package com._4paradigm.prophet.LicenseMangement.service;

import com._4paradigm.prophet.LicenseMangement.entity.User;
import com._4paradigm.prophet.LicenseMangement.entity.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FindUser {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public UserResult findUser(User user){
        String sql = "select * from user where name = ? and password = ?";
        Object [] args = {user.getName(),user.getPassword()};
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,args);
        System.out.println("数据是："+list);
        UserResult userResult = new UserResult();
        if(list.isEmpty()) {
            userResult.setResult(false);
        }
        else {
            userResult.setResult(true);
            User user1 = new User();
            user1.setPassword((String) list.get(0).get("password"));
            user1.setName((String) list.get(0).get("name"));
            user1.setId((Integer) list.get(0).get("id"));
            user1.setLevel((Integer) list.get(0).get("level"));
            System.out.println("将数据库读出的user的list信息注入给user对象后，user对象内容："+user1);
            userResult.setUser(user1);
        }
        return userResult;
    }
}
