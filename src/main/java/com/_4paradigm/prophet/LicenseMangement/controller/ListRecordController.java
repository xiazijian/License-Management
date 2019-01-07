package com._4paradigm.prophet.LicenseMangement.controller;

import com._4paradigm.prophet.LicenseMangement.entity.LicenseRecord;
import com._4paradigm.prophet.LicenseMangement.service.LicenseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api")
public class ListRecordController {
    @Autowired
    private LicenseRecordService licenseRecordService;
    @GetMapping(value="listRecord")
    public List<LicenseRecord> getList(){
        System.out.println("LicenseRecord:"+licenseRecordService.getList());
        return licenseRecordService.getList();
    }
}
