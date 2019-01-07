package com._4paradigm.prophet.LicenseMangement.controller;

import com._4paradigm.prophet.LicenseMangement.entity.LicenseRecord;
import com._4paradigm.prophet.LicenseMangement.service.LicenseRecordService;
import com._4paradigm.prophet.LicenseMangement.service.UpdateLicenseRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api")
public class PassApply {
    @Autowired
    private UpdateLicenseRecord updateLicenseRecord;
    @Autowired
    private LicenseRecordService licenseRecordService;
    @GetMapping(value = "passApply")
    public List<LicenseRecord> passApply (@RequestParam(name="id") int id){
        System.out.println("前端传过来的参数ID："+id);
        updateLicenseRecord.updateLicenseRecord(id,1);
        return licenseRecordService.getList();
    }
}
