package com._4paradigm.prophet.LicenseMangement.aop.aopController;

import com._4paradigm.prophet.LicenseMangement.aop.aopService.AopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AopController {

    @Autowired
    AopService aopService;


    @GetMapping(value = "/aop1/{id}")
    public String aopTest(){
        System.out.println("进入 controller");
        return aopService.service();
    }
    @GetMapping(value = "/aop2/{id}")
    public String aopException(){
        System.out.println("进入controller:aopException");
        String x="x";
        try{
            x= aopService.testException();
        }catch (Exception e){
            System.out.println("exception:");
            e.printStackTrace();
        }
        System.out.println("hahahah");
        for(int i=0;i<10;i++){
            System.out.println(i);
        }
        return x;

    }

}