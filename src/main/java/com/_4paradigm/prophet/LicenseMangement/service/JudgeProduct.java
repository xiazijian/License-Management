package com._4paradigm.prophet.LicenseMangement.service;

import com._4paradigm.prophet.LicenseMangement.entity.PASLicense;
import com._4paradigm.prophet.LicenseMangement.entity.PWSLicense;
import com._4paradigm.prophet.LicenseMangement.entity.ProphetLicense;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class JudgeProduct {
    public static String judgeProductGenerateLicense(Map<String,String> map) throws Exception {
        String product = map.get("product");
        String result = null;
        switch (product){
            case "PAS":
                PASLicense pasLicense = new PASLicense();
                pasLicense.setMachineCode(map.get("machineCode"));
                pasLicense.setMaxCpuCores(Integer.valueOf(map.get("MaxCpuCores")));
                pasLicense.setMaxGpuUnits(Integer.valueOf(map.get("MaxGpuUnits")));
                pasLicense.setExpiredDate(map.get("ExpiredDate"));
                pasLicense.setIssuedDate(map.get("IssuedDate"));
                List<String> list =new ArrayList<>();
                String[] str= map.get("versionsSupported").split(",");
                for(int i =0;i<str.length;++i){
                    list.add(str[i]);
                }
                pasLicense.setVersionsSupported(list);
                result = GenerateLicense.generateLicense(pasLicense);
                break;
            case "PWS":
                PWSLicense pwsLicense = new PWSLicense();
                pwsLicense.setMachineCode(map.get("machineCode"));
                pwsLicense.setMaxCpuCores(Integer.valueOf(map.get("MaxCpuCores")));
                pwsLicense.setMaxGpuUnits(Integer.valueOf(map.get("MaxGpuUnits")));
                pwsLicense.setExpiredDate(map.get("ExpiredDate"));
                pwsLicense.setIssuedDate(map.get("IssuedDate"));
                List<String> list1 =new ArrayList<>();
                String[] str1= map.get("versionsSupported").split(",");
                for(int i =0;i<str1.length;++i){
                    list1.add(str1[i]);
                }
                pwsLicense.setVersionsSupported(list1);
                result = GenerateLicense.generateLicense(pwsLicense);
                break;
            case "prophet":
                ProphetLicense prophetLicense = new ProphetLicense();
                prophetLicense.setMachineCode(map.get("machineCode"));
                prophetLicense.setExpiredDate(map.get("ExpiredDate"));
                prophetLicense.setIssuedDate(map.get("IssuedDate"));
                List<String> list2 =new ArrayList<>();
                String[] str2= map.get("versionsSupported").split(",");
                for(int i =0;i<str2.length;++i){
                    list2.add(str2[i]);
                }
                prophetLicense.setVersionsSupported(list2);
                prophetLicense.setCustomer(map.get("customer"));
                prophetLicense.setMaxTenantCount(Integer.valueOf(map.get("maxTenantCount")));

                result = GenerateLicense.generateLicense(prophetLicense);
                break;
        }

        return result;
    }
}
