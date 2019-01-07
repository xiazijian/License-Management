package com._4paradigm.prophet.LicenseMangement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UpdateLicenseRecord {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateLicenseRecord(int id,int isEffect){
        String sql = "update license_record set isEffect = ? where id = ?";
        Object[] args ={isEffect,id};
        jdbcTemplate.update(sql,args);
    }
}
