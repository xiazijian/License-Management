package com._4paradigm.prophet.LicenseMangement.controller;

import com._4paradigm.prophet.LicenseMangement.entity.LicenseRecord;
import com._4paradigm.prophet.LicenseMangement.service.LicenseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value="userListRecord")
    public List<LicenseRecord> getUserList(@RequestParam(name = "name") String name){
        System.out.println("LicenseRecord:"+licenseRecordService.getUserList(name,0));
        return licenseRecordService.getUserList(name,0);
    }
    @GetMapping(value="userListPassRecord")
    public List<LicenseRecord> getUserPassList(@RequestParam(name = "name") String name){
        System.out.println("LicenseRecord:"+licenseRecordService.getUserPassList(name,1));
        return licenseRecordService.getUserPassList(name,1);
    }
    @GetMapping(value="userListRefudeRecord")
    public List<LicenseRecord> getUserRefudeList(@RequestParam(name = "name") String name){
        System.out.println("LicenseRecord:"+licenseRecordService.getUserList(name,2));
        return licenseRecordService.getUserList(name,2);
    }
}
