package com._4paradigm.prophet.LicenseMangement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class InsertLicenseRecord {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public  void insert(Map<String,String> map){
        String sql = "insert into license_record (name,license,configuration) values (?,?,?)";
        Object args[] = {map.get("name"),map.get("license"),map.get("configuration")};
        jdbcTemplate.update(sql, args);

    }
}
