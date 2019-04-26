package com._4paradigm.prophet.LicenseMangement.controller;

import com._4paradigm.prophet.LicenseMangement.service.HdfsUtils;
import com._4paradigm.prophet.LicenseMangement.service.InsertLicenseRecord;
import com._4paradigm.prophet.LicenseMangement.service.JudgeProduct;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    @Autowired
    private HdfsUtils hdfsUtils;

    @PostMapping(value="he")
    public void ha(HttpEntity httpEntity){
        System.out.println(httpEntity.getBody());
        Map json = (Map) httpEntity.getBody();
        System.out.println(json.get("algo"));
        System.out.println(json.get("map"));
        Map<String,String> map = (Map<String, String>) json.get("map");
        System.out.println(map.get("a"));
    }

    @GetMapping(value = "/create")
    public void create(HttpServletResponse response ) throws IOException {
        String path = "/user/xiaweiyi/he.txt";
        File file = new File("/Users/xiaweiyi/Downloads/he.txt");
        System.out.println(file.length());
        FileInputStream fileInputStream = new FileInputStream(file);
        //hdfsUtils.createFile(path,fileInputStream);

    }
    @GetMapping(value = "/testHdfs")
    public String testHdfs(HttpServletResponse response) throws IOException {
        try {
            //hdfsUtils.readFile("/user/xiaweiyi/algo/OCR/classification/filesMD5.txt");
           hdfsUtils.downloadExperiment("/test",response);
            //response = hdfsUtils.downloadFile(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "hehe";

    }

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
