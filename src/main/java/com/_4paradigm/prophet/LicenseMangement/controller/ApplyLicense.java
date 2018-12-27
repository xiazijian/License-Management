package com._4paradigm.prophet.LicenseMangement.controller;

import com._4paradigm.prophet.LicenseMangement.service.InsertLicenseRecord;
import com._4paradigm.prophet.LicenseMangement.service.JudgeProduct;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin
@RestController
@RequestMapping(value = "api")
public class ApplyLicense {
    @Autowired
    private InsertLicenseRecord insertLicenseRecord;


    @PostMapping(value="generate")
    public String generateLicense(HttpEntity httpEntity, HttpServletResponse response)  {
        System.out.println(httpEntity.getBody());
        System.out.println("json:"+JSON.toJSONString(httpEntity.getBody()));
        Map json = (Map) httpEntity.getBody();
        String IssuedDate = (String) json.get("IssuedDate");
        String ExpiredDate = (String) json.get("ExpiredDate");
        System.out.println(IssuedDate+"|"+ExpiredDate);
        String config = JSON.toJSONString(httpEntity.getBody());
        String result=null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        try{
            simpleDateFormat.parse(IssuedDate);
            simpleDateFormat.parse(ExpiredDate);
            try{
                result = JudgeProduct.judgeProductGenerateLicense(json);
                Map<String,String> map = new HashMap<>();
                map.put("name", (String) json.get("username"));
                map.put("license",result);
                map.put("configuration",config.substring(1,config.length()-1));
                insertLicenseRecord.insert(map);
                System.out.println(result);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }catch (ParseException e){
            response.setStatus(500);
        }
        return result;
    }
}
