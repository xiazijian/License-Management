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
    @GetMapping(value = "LicenseRecordCount")
    public int getLicenseRecordCount(){
        System.out.println("数据数目："+licenseRecordService.getLiceneRecordCount());
        return licenseRecordService.getLiceneRecordCount();
    }
    @GetMapping(value = "LicenseRefuseRecordCount")
    public int getLicenseRefuseRecordCount(){
        System.out.println("数据数目："+licenseRecordService.getLiceneRefuseRecordCount());
        return licenseRecordService.getLiceneRefuseRecordCount();
    }
    @GetMapping(value = "LicensePassRecordCount")
    public int getLicensePassRecordCount(){
        System.out.println("数据数目："+licenseRecordService.getLicenePassRecordCount());
        return licenseRecordService.getLicenePassRecordCount();
    }
    @GetMapping(value="getLimitList")
    public List<LicenseRecord> getLimitList(@RequestParam(name="start") String start,@RequestParam(name="limit") int limit){
        System.out.println("LicenseRecord:"+licenseRecordService.getLimitList(Integer.valueOf(start), limit));
        return licenseRecordService.getLimitList(Integer.valueOf(start), limit);
    }
    @GetMapping(value="listRecord")
    public List<LicenseRecord> getList(){
        System.out.println("LicenseRecord:"+licenseRecordService.getList());
        return licenseRecordService.getList();
    }
    @GetMapping(value="listAllPassRecordLimit")
    public List<LicenseRecord> getAllPassLimitList(@RequestParam(name="start") String start,@RequestParam(name="limit") int limit){
        System.out.println("listAllPassRecord:"+licenseRecordService.getAllPassLimitList(Integer.valueOf(start), limit));
        return licenseRecordService.getAllPassLimitList(Integer.valueOf(start), limit);
    }
    @GetMapping(value="listAllPassRecord")
    public List<LicenseRecord> getAllPassList(){
        System.out.println("listAllPassRecord:"+licenseRecordService.getAllPassList());
        return licenseRecordService.getAllPassList();
    }
    @GetMapping(value="listAllRefuseLimitRecord")
    public List<LicenseRecord> geAllRefusetLimitList(@RequestParam(name="start") String start,@RequestParam(name="limit") int limit){
        System.out.println("listAllRefuseRecord:"+licenseRecordService.geAllRefusetLimitList(Integer.valueOf(start), limit));
        return licenseRecordService.geAllRefusetLimitList(Integer.valueOf(start), limit);
    }
    @GetMapping(value="listAllRefuseRecord")
    public List<LicenseRecord> geAllRefusetList(){
        System.out.println("listAllRefuseRecord:"+licenseRecordService.geAllRefusetList());
        return licenseRecordService.geAllRefusetList();
    }
    @GetMapping(value="userListRecord")
    public List<LicenseRecord> getUserList(@RequestParam(name = "name") String name){
        System.out.println("userListRecord:"+licenseRecordService.getUserList(name,0));
        return licenseRecordService.getUserList(name,0);
    }
    @GetMapping(value="userListPassRecord")
    public List<LicenseRecord> getUserPassList(@RequestParam(name = "name") String name){
        System.out.println("userListPassRecord:"+licenseRecordService.getUserPassList(name,1));
        return licenseRecordService.getUserPassList(name,1);
    }
    @GetMapping(value="userListRefudeRecord")
    public List<LicenseRecord> getUserRefudeList(@RequestParam(name = "name") String name){
        System.out.println("userListRefudeRecord:"+licenseRecordService.getUserList(name,2));
        return licenseRecordService.getUserList(name,2);
    }
}
