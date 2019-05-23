package com._4paradigm.prophet.LicenseMangement.aop.aopService;

import org.springframework.stereotype.Service;

@Service
public class AopService {
    public String service(){
        System.out.println("进入service");
        return "hehe";
    }
    public String testException(){
        int[] x= new int[1];
        x[3]=1;
        return "h";
    }
}