package com._4paradigm.prophet.LicenseMangement.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Schedule {
    @Scheduled(cron = "0/2 * * * * ?")//每2秒执行一次
    public void run() throws Exception{
        System.out.println("执行定时任务");
    }
}