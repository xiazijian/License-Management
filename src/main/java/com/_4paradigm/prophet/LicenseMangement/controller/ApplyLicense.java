package com._4paradigm.prophet.LicenseMangement.controller;

import com._4paradigm.prophet.LicenseMangement.service.InsertLicenseRecord;
import com._4paradigm.prophet.LicenseMangement.service.JudgeProduct;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin
@RestController
public class ApplyLicense {
    @Autowired
    private InsertLicenseRecord insertLicenseRecord;


    @PostMapping(value="generate")
    public String generateLicense(HttpEntity httpEntity, HttpServletResponse response)  {
        System.out.println(httpEntity.getBody());
        System.out.println("json:"+JSON.toJSONString(httpEntity.getBody()));
        Map json = (Map) httpEntity.getBody();
        String config = JSON.toJSONString(httpEntity.getBody());
        String result=null;
        try{
            result = JudgeProduct.judgeProductGenerateLicense(json);
            Map<String,String> map = new HashMap<>();
            map.put("name", (String) json.get("username"));
            map.put("license",result);
            map.put("configuration",config.substring(1,config.length()-1));
            insertLicenseRecord.insert(map);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
}
